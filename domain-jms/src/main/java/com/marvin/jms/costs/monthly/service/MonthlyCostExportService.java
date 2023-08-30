package com.marvin.jms.costs.monthly.service;

import com.marvin.common.costs.monthly.dao.MonthlyCostDAO;
import com.marvin.common.costs.monthly.dto.MonthlyCostDTO;
import com.marvin.common.costs.monthly.entity.MonthlyCostEntity;
import com.marvin.jms.costs.AbstractCostExport;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;

@Singleton
public class MonthlyCostExportService extends AbstractCostExport<MonthlyCostEntity, MonthlyCostDTO> {

    @Resource(lookup = "java:/jms/queue/costs/export/monthly")
    private Queue queue;

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @EJB
    private MonthlyCostDAO monthlyCostDAO;

    @Override
    @PostConstruct
    public void init() {
        super.queue = queue;
        super.connectionFactory = connectionFactory;
        super.dataAccessObject = monthlyCostDAO;
    }

    @Override
    protected MonthlyCostDTO map(MonthlyCostEntity entity) {
        return new MonthlyCostDTO(entity.getCostDate(), entity.getValue());
    }
}
