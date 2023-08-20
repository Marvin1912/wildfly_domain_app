package com.marvin.wildfly_domain_app.costs.special.dto;

import java.time.LocalDate;
import java.util.List;

public record SpecialCostDTO(LocalDate costDate, List<SpecialCostEntryDTO> entries) {
}
