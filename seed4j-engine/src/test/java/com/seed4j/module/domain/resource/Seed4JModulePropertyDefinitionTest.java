package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModulePropertyDefinitionTest {

  @Test
  void shouldHaveMeaningfulToString() {
    assertThat(projectNameProperty()).hasToString(
      """
      Seed4JModulePropertyDefinition[type=STRING,key=projectName,mandatory=true,description=Project full name,defaultValue=Seed4J Sample Application,order=-200]\
      """
    );
  }

  @Test
  void shouldHaveMeaningfulToStringForPropertyDefinitionWithMinimalInfos() {
    assertThat(optionalBooleanProperty("foo").build()).hasToString(
      """
      Seed4JModulePropertyDefinition[type=BOOLEAN,key=foo,mandatory=false,description=,defaultValue=,order=0]\
      """
    );
  }
}
