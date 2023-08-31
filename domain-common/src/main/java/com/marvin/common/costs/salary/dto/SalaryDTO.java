package com.marvin.common.costs.salary.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalaryDTO(LocalDate salaryDate, BigDecimal value) {
}
