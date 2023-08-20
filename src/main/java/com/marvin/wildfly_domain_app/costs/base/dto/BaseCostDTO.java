package com.marvin.wildfly_domain_app.costs.base.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BaseCostDTO(LocalDate costDate, BigDecimal value) {
}
