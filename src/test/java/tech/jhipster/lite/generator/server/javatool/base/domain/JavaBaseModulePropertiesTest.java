package tech.jhipster.lite.generator.server.javatool.base.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.module.domain.JHipsterBasePackage;
import tech.jhipster.lite.generator.module.domain.JHipsterProjectBaseName;
import tech.jhipster.lite.generator.module.domain.JHipsterProjectFolder;
import tech.jhipster.lite.generator.project.domain.Project;

@UnitTest
class JavaBaseModulePropertiesTest {

  @Test
  void shouldConvertFromProject() {
    Project project = TestUtils.tmpProjectBuilder().config(Map.of("packageName", "my/package", "baseName", "myProject")).build();
    JavaBaseModuleProperties properties = JavaBaseModuleProperties.from(project);

    assertThat(properties.project()).isEqualTo(new JHipsterProjectFolder(project.getFolder()));
    assertThat(properties.basePackage()).isEqualTo(new JHipsterBasePackage("my.package"));
    assertThat(properties.projectBaseName()).isEqualTo(new JHipsterProjectBaseName("myProject"));
  }
}
