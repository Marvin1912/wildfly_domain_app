package com.marvin.wildfly_domain_app.costs.daily.service;

import com.marvin.wildfly_domain_app.costs.daily.entity.DailyCostEntity;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Local
@Stateless
public class DailyCostSessionBean {

    @PersistenceContext(unitName = "wildfly-domain")
    private EntityManager entityManager;

    public List<DailyCostEntity> getDailyCosts(int year, int month) {
        return entityManager.createNamedQuery("findByYearAndMonth", DailyCostEntity.class)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
    }

}
