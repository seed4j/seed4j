package tech.jhipster.lite.generator.server.springboot.technicaltools.gitinfo.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GitInfoModuleFactoryTest {

  private static final GitInfoModuleFactory factory = new GitInfoModuleFactory();

  @Test
  void shouldAddGitInformation() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.myapp")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    //@formatter:off
    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
        .containing(
          """
                <plugin>
                  <groupId>io.github.git-commit-id</groupId>
                  <artifactId>git-commit-id-maven-plugin</artifactId>
                </plugin>
          """
        )
        .and()
      .hasFile("src/main/resources/config/application.properties")
        .containing("# Git Information")
        .containing("management.info.git.mode=full")
        .containing("management.info.git.enabled=true")
        .containing("management.info.env.enabled=true")
        .and()
      .hasFile("src/main/java/tech/jhipster/myapp/gitinfo/infrastructure/primary/GitInfoConfiguration.java");
    //@formatter:on
  }
}
