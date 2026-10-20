package com.seed4j.shared.error.infrastructure.primary;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(NativeValidationHints.class)
@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native configuration")
@Configuration
class NativeValidationConfiguration {}
