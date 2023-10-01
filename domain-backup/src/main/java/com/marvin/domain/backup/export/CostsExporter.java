package com.marvin.domain.backup.export;

import com.marvin.common.costs.daily.dao.DailyCostDAO;
import com.marvin.common.costs.daily.dto.DailyCostDTO;
import com.marvin.common.costs.daily.entity.DailyCostEntity;
import com.marvin.common.costs.monthly.dao.MonthlyCostDAO;
import com.marvin.common.costs.monthly.dto.MonthlyCostDTO;
import com.marvin.common.costs.monthly.entity.MonthlyCostEntity;
import com.marvin.common.costs.salary.dao.SalaryDAO;
import com.marvin.common.costs.salary.dto.SalaryDTO;
import com.marvin.common.costs.salary.entity.SalaryEntity;
import com.marvin.common.costs.special.dao.SpecialCostEntryDAO;
import com.marvin.common.costs.special.dto.SpecialCostDTO;
import com.marvin.common.costs.special.dto.SpecialCostEntryDTO;
import com.marvin.common.costs.special.entity.SpecialCostEntryEntity;
import com.marvin.domain.backup.upload.Uploader;
import com.marvin.domain.backup.write.CostExportFileWriter;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class CostsExporter implements CostsExporterLocal {

    private static final Function<DailyCostEntity, DailyCostDTO> DAILY_COST_MAPPER = dailyCostEntity ->
            new DailyCostDTO(dailyCostEntity.getCostDate(), dailyCostEntity.getValue());
    private static final Function<MonthlyCostEntity, MonthlyCostDTO> MONTHLY_COST_MAPPER = monthlyCostEntity ->
            new MonthlyCostDTO(monthlyCostEntity.getCostDate(), monthlyCostEntity.getValue());
    public static final Function<SpecialCostEntryEntity, SpecialCostEntryDTO> SPECIAL_COST_ENTRY_MAPPER = e ->
            new SpecialCostEntryDTO(e.getDescription(), e.getValue());
    public static final Function<Map.Entry<LocalDate, List<SpecialCostEntryDTO>>, SpecialCostDTO> SPECIAL_COST_MAPPER = e ->
            new SpecialCostDTO(e.getKey(), e.getValue());
    public static final Function<SalaryEntity, SalaryDTO> SALARY_MAPPER = salaryEntity ->
            new SalaryDTO(salaryEntity.getSalaryDate(), salaryEntity.getValue());

    private static final DateTimeFormatter FILE_DTF = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");

    @EJB
    private DailyCostDAO dailyCostDAO;

    @EJB
    private MonthlyCostDAO monthlyCostDAO;

    @EJB
    private SpecialCostEntryDAO specialCostEntryDAO;

    @EJB
    private SalaryDAO salaryDAO;

    @EJB
    private CostExportFileWriter costExportFileWriter;

    @EJB
    private CostsExporterLocal selfReference;

    @Inject
    private Uploader uploader;

    @Inject
    private ExportConfig exportConfig;

    @Override
    public void exportCosts() {

        String now = LocalDateTime.now().format(FILE_DTF);
        String costExportFolder = exportConfig.getCostExportFolder();

        final Path dailyCostsPath = Path.of(costExportFolder + "/daily_costs_" + now + ".json");
        selfReference.exportCost(
                dailyCostsPath,
                () -> dailyCostDAO.getAll().map(DAILY_COST_MAPPER)
        );

        final Path monthlyCostsPath = Path.of(costExportFolder + "/monthly_costs_" + now + ".json");
        selfReference.exportCost(
                monthlyCostsPath,
                () -> monthlyCostDAO.getAll().map(MONTHLY_COST_MAPPER)
        );

        final Path specialCostsPath = Path.of(costExportFolder + "/special_costs_" + now + ".json");
        selfReference.exportCost(
                specialCostsPath,
                () -> specialCostEntryDAO.getAll()
                        .collect(
                                Collectors.groupingBy(
                                        e -> e.getSpecialCost().getCostDate(),
                                        Collectors.mapping(SPECIAL_COST_ENTRY_MAPPER, Collectors.toList())
                                )
                        ).entrySet().stream()
                        .map(SPECIAL_COST_MAPPER)
        );

        final Path salariesPath = Path.of(costExportFolder + "/salaries_" + now + ".json");
        selfReference.exportCost(
                salariesPath,
                () -> salaryDAO.getAll().map(SALARY_MAPPER)
        );

        uploader.zipAndUploadCostFiles(List.of(
                dailyCostsPath,
                monthlyCostsPath,
                specialCostsPath,
                salariesPath
        ));
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> void exportCost(Path path, Supplier<Stream<T>> costs) {
        costExportFileWriter.writeFile(path, costs.get());
    }
}
