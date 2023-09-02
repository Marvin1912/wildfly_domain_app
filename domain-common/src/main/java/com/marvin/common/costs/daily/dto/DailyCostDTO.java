package com.marvin.common.costs.daily.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyCostDTO(LocalDate costDate, BigDecimal value) {
}
