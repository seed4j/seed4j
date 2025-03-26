package tech.jhipster.lite.module.domain.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulePropertyDefinition.*;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterModulePropertiesDefinitionTest {

  @Test
  void shouldDeduplicateProperties() {
    Collection<JHipsterModulePropertyDefinition> properties = JHipsterModulePropertiesDefinition.builder()
      .addIndentation()
      .addBasePackage()
      .addBasePackage()
      .build()
      .get();

    assertThat(properties).usingRecursiveFieldByFieldElementComparator().containsExactly(basePackageProperty(), indentationProperty());
  }

  @Test
  void shouldHaveMeaningfulToString() {
    var definition = JHipsterModulePropertiesDefinition.builder().add(basePackageProperty()).build();
    assertThat(definition).hasToString(
      """
      JHipsterModulePropertiesDefinition[definitions=[JHipsterModulePropertyDefinition[type=STRING,key=packageName,mandatory=true,description=Base java package,defaultValue=com.mycompany.myapp,order=-300]]]\
      """
    );
  }
}
