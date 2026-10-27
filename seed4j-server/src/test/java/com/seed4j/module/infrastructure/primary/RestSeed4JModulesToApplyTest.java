package com.seed4j.module.infrastructure.primary;

import static com.seed4j.BeanValidationAssertions.assertThatBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeed4JModulesToApplyTest {

  private static final ProjectFolder projectFolder = mock(ProjectFolder.class);

  @Test
  void shouldDeserializeFromJson() {
    assertThat(JsonHelper.readFromJson(json(), RestSeed4JModulesToApply.class).toDomain(projectFolder))
      .usingRecursiveComparison()
      .isEqualTo(Seed4JModulesFixture.modulesToApply());
  }

  private static String json() {
    return """
      {
      "modules": ["maven-java", "init"],
      "properties":
        {
          "projectFolder": "/dummy",
          "parameters": {
            "projectName": "Growth Project",
            "baseName": "growth",
            "packageName": "com.seed4j.growth",
            "serverPort": 8080
          }
        }
    }
    """;
  }

  @Test
  @SuppressWarnings("NullAway")
  void shouldValidateModulesToApplyWithoutAttributes() {
    assertThatBean(new RestSeed4JModulesToApply(null, null)).hasInvalidProperty("modules").and().hasInvalidProperty("properties");
  }

  @Test
  @SuppressWarnings("NullAway")
  void shouldValidateModulesToApplyWithEmptyModules() {
    assertThatBean(new RestSeed4JModulesToApply(List.of(), null)).hasInvalidProperty("modules");
  }
}
