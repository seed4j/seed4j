package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.Collection;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModulePropertiesDefinitionTest {

  @Test
  void shouldDeduplicateProperties() {
    Collection<Seed4JModulePropertyDefinition> properties = Seed4JModulePropertiesDefinition.builder()
      .addIndentation()
      .addBasePackage()
      .addBasePackage()
      .build()
      .get();

    assertThat(properties).usingRecursiveFieldByFieldElementComparator().containsExactly(basePackageProperty(), indentationProperty());
  }

  @Test
  void shouldHaveMeaningfulToString() {
    var definition = Seed4JModulePropertiesDefinition.builder().add(basePackageProperty()).build();
    assertThat(definition).hasToString(
      """
      Seed4JModulePropertiesDefinition[definitions=[Seed4JModulePropertyDefinition[type=STRING,key=packageName,mandatory=true,description=Base java package,defaultValue=com.mycompany.myapp,order=-300]]]\
      """
    );
  }
}
