package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ImportRuntimeHints(NativeHints.class)
@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native configuration")
@Configuration
public class NativeJavaDependencyConfiguration {}
