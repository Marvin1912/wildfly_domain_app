package com.marvin.wildfly_domain_app.costs.salary.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalaryDTO(LocalDate costDate, BigDecimal value) {
}
