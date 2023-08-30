package com.marvin.common.costs.base.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BaseCostDTO(LocalDate costDate, BigDecimal value) {
}
