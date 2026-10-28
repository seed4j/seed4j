package com.seed4j.module.infrastructure.secondary.git;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.springframework.context.annotation.*;

@ImportRuntimeHints(NativeHints.class)
@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native configuration")
@Configuration
class NativeJGitConfiguration {}
