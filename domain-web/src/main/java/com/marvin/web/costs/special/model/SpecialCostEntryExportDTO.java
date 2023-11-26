package com.marvin.web.costs.special.model;

import java.math.BigDecimal;

public record SpecialCostEntryExportDTO(
        BigDecimal value,
        String description
) {
}
