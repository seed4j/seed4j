package com.seed4j.module.infrastructure.primary;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import org.junit.jupiter.api.Test;

@UnitTest
class RestSeedModulePropertiesTest {

  private static final ProjectFolder jHipsterProjectFolderFactory = mock(ProjectFolder.class);

  @Test
  void shouldNotConvertToPropertiesWithInvalidProjectFolder() {
    when(jHipsterProjectFolderFactory.isInvalid("/test")).thenReturn(true);

    assertThatThrownBy(() ->
      JsonHelper.readFromJson(json(), RestSeedModuleProperties.class).toDomain(jHipsterProjectFolderFactory)
    ).isExactlyInstanceOf(InvalidProjectFolderException.class);
  }

  @Test
  void shouldConvertToProperties() {
    assertThat(JsonHelper.readFromJson(json(), RestSeedModuleProperties.class).toDomain(jHipsterProjectFolderFactory))
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
        "projectName": "JHipster project",
        "baseName": "jhipster",
        "optionalString": "optional",
        "mandatoryInteger": 42,
        "mandatoryBoolean": true,
        "optionalBoolean": true
      }
    }
      """;
  }
}
