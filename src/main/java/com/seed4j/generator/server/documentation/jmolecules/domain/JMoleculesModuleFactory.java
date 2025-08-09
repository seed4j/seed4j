package com.seed4j.generator.server.documentation.jmolecules.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.javadependency.JavaDependencyScope.IMPORT;
import static com.seed4j.module.domain.javadependency.JavaDependencyType.POM;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class JMoleculesModuleFactory {

  private static final GroupId JMOLECULES_GROUP_ID = groupId("org.jmolecules");

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependencyManagement(jMoleculesBomDependency())
        .addDependency(JMOLECULES_GROUP_ID, artifactId("jmolecules-ddd"))
        .addDependency(JMOLECULES_GROUP_ID, artifactId("jmolecules-hexagonal-architecture"))
        .addDependency(JMOLECULES_GROUP_ID, artifactId("jmolecules-events"))
        .and()
      .build();
    // @formatter:on
  }

  private JavaDependency jMoleculesBomDependency() {
    return javaDependency()
      .groupId(JMOLECULES_GROUP_ID)
      .artifactId("jmolecules-bom")
      .versionSlug("jmolecules-bom")
      .type(POM)
      .scope(IMPORT)
      .build();
  }
}
