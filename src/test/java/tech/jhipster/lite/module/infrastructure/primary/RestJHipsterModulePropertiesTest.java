package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.allProperties;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.projectfolder.domain.ProjectFolder;

@UnitTest
class RestJHipsterModulePropertiesTest {

  private static final ProjectFolder jHipsterProjectFolderFactory = mock(ProjectFolder.class);

  @Test
  void shouldNotConvertToPropertiesWithInvalidProjectFolder() {
    when(jHipsterProjectFolderFactory.isInvalid("/test")).thenReturn(true);

    assertThatThrownBy(
      () -> JsonHelper.readFromJson(json(), RestJHipsterModuleProperties.class).toDomain(jHipsterProjectFolderFactory, List.of(), List.of())
    ).isExactlyInstanceOf(InvalidProjectFolderException.class);
  }

  @Test
  void shouldConvertToProperties() {
    assertThat(
      JsonHelper.readFromJson(json(), RestJHipsterModuleProperties.class).toDomain(jHipsterProjectFolderFactory, List.of(), List.of())
    )
      .usingRecursiveComparison()
      .isEqualTo(allProperties());
  }

  private static String json() {
    return """
    {
      "projectFolder": "/test",
      "commit": true,
      "parameters": {
        "packageName": "tech.jhipster.chips",
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
