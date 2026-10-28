package com.seed4j.history.infrastructure.secondary;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(NativeHints.class)
@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native configuration")
@Configuration
class NativeProjectHistoryConfiguration {}
