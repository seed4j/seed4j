package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.Collection;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedModulePropertiesDefinitionTest {

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
