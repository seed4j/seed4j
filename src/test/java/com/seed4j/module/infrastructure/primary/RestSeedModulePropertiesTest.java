package com.seed4j.module.infrastructure.primary;

import static com.seed4j.module.domain.SeedModulesFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeedModulePropertiesTest {

  private static final ProjectFolder projectFolder = mock(ProjectFolder.class);

  @Test
  void shouldNotConvertToPropertiesWithInvalidProjectFolder() {
    when(projectFolder.isInvalid("/test")).thenReturn(true);

    assertThatThrownBy(() -> JsonHelper.readFromJson(json(), RestSeedModuleProperties.class).toDomain(projectFolder)).isExactlyInstanceOf(
      InvalidProjectFolderException.class
    );
  }

  @Test
  void shouldConvertToProperties() {
    assertThat(JsonHelper.readFromJson(json(), RestSeedModuleProperties.class).toDomain(projectFolder))
      .usingRecursiveComparison()
      .isEqualTo(allProperties());
  }

  private static String json() {
    return """
    {
      "projectFolder": "/test",
      "commit": true,
      "parameters": {
        "packageName": "com.seed4j.growth",
        "indentSize": 2,
        "projectName": "Seed4J project",
        "baseName": "seed4j",
        "optionalString": "optional",
        "mandatoryInteger": 42,
        "mandatoryBoolean": true,
        "optionalBoolean": true
      }
    }
      """;
  }
}
