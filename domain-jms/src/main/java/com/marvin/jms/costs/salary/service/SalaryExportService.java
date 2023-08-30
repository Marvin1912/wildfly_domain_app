package com.marvin.jms.costs.salary.service;

import com.marvin.common.costs.salary.dao.SalaryDAO;
import com.marvin.common.costs.salary.dto.SalaryDTO;
import com.marvin.common.costs.salary.entity.SalaryEntity;
import com.marvin.jms.costs.AbstractCostExport;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;

@Singleton
public class SalaryExportService extends AbstractCostExport<SalaryEntity, SalaryDTO> {

    @Resource(lookup = "java:/jms/queue/costs/export/salary")
    private Queue queue;

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @EJB
    private SalaryDAO salaryDAO;

    @Override
    @PostConstruct
    public void init() {
        super.queue = queue;
        super.connectionFactory = connectionFactory;
        super.dataAccessObject = salaryDAO;
    }

    @Override
    protected SalaryDTO map(SalaryEntity entity) {
        return new SalaryDTO(entity.getSalaryDate(), entity.getValue());
    }
}
