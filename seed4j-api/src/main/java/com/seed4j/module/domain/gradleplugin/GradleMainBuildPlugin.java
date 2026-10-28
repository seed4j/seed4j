package com.seed4j.module.domain.gradleplugin;

public sealed interface GradleMainBuildPlugin extends GradlePlugin permits GradleCorePlugin, GradleCommunityPlugin {}
