package com.marvin.jms.costs.daily.service;

import com.marvin.common.costs.daily.dao.DailyCostDAO;
import com.marvin.common.costs.daily.dto.DailyCostDTO;
import com.marvin.common.costs.daily.entity.DailyCostEntity;
import com.marvin.jms.costs.AbstractCostExport;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;

@Singleton
public class DailyCostExportService extends AbstractCostExport<DailyCostEntity, DailyCostDTO> {

    @Resource(lookup = "java:/jms/queue/costs/export/daily")
    private Queue queue;

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @EJB
    private DailyCostDAO dailyCostDAO;

    @Override
    @PostConstruct
    public void init() {
        super.queue = queue;
        super.connectionFactory = connectionFactory;
        super.dataAccessObject = dailyCostDAO;
    }

    @Override
    protected DailyCostDTO map(DailyCostEntity entity) {
        return new DailyCostDTO(entity.getCostDate(), entity.getValue());
    }
}
