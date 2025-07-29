package com.seed4j.module.domain.gradleplugin;

public sealed interface GradleProfilePlugin extends GradlePlugin permits GradleCorePlugin, GradleCommunityProfilePlugin {}
