package com.marvin.web.costs.special.model;

import java.time.LocalDate;
import java.util.List;

public record SpecialCostExportDTO(
        LocalDate costDate,
        List<SpecialCostEntryExportDTO> entries
) {
}
