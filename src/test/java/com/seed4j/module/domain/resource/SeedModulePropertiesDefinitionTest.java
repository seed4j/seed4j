package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.Collection;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedModulePropertiesDefinitionTest {

  @Test
  void shouldDeduplicateProperties() {
    Collection<SeedModulePropertyDefinition> properties = SeedModulePropertiesDefinition.builder()
      .addIndentation()
      .addBasePackage()
      .addBasePackage()
      .build()
      .get();

    assertThat(properties).usingRecursiveFieldByFieldElementComparator().containsExactly(basePackageProperty(), indentationProperty());
  }

  @Test
  void shouldHaveMeaningfulToString() {
    var definition = SeedModulePropertiesDefinition.builder().add(basePackageProperty()).build();
    assertThat(definition).hasToString(
        """
        SeedModulePropertiesDefinition[definitions=[SeedModulePropertyDefinition[type=STRING,key=packageName,mandatory=true,description=Base java package,defaultValue=com.mycompany.myapp,order=-300]]]\
        """
      );
  }
}
