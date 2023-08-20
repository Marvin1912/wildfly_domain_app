package com.marvin.wildfly_domain_app.costs.special.dto;

import java.math.BigDecimal;

public record SpecialCostEntryDTO(String description, BigDecimal value) {
}
