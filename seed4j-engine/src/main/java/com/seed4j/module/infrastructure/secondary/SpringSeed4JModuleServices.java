package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import org.springframework.stereotype.Component;

@Component
record SpringSeed4JModuleServices(
  DockerImages dockerImages,
  NodeVersions nodeVersions,
  NodeLazyPackagesInstaller nodeLazyPackagesInstaller
) implements Seed4JModuleServices {}
