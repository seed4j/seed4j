package tech.jhipster.lite.module.infrastructure.secondary.git;

import org.springframework.context.annotation.*;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ImportRuntimeHints(NativeHints.class)
@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native configuration")
@Configuration
class NativeJGitConfiguration {}
