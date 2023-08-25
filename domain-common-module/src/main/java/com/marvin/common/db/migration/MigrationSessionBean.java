package com.marvin.common.db.migration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class MigrationSessionBean {

    @Resource(lookup = "java:jboss/datasources/PostGreDS")
    private DataSource dataSource;

    @PostConstruct
    public void initFlyWay() {

        final Flyway flyway = Flyway.configure()
                .cleanDisabled(false)
                .dataSource(dataSource)
                .load();

        flyway.migrate();
    }
}
