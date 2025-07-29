package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.*;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
class PropertiesFileSpringFactoriesHandlerTest {

  public static final Path EXISTING_SPRING_FACTORIES = Path.of(
    "src/test/resources/projects/project-with-spring-factories/spring.factories"
  );

  @Test
  void shouldCreateUnknownFile() {
    Path factoriesFile = Path.of(tmpDirForTest(), "src/test/resources/META-INF/spring.factories");
    PropertiesFileSpringFactoriesHandler handler = new PropertiesFileSpringFactoriesHandler(factoriesFile);

    handler.append(propertyKey("o.s.c.ApplicationListener"), propertyValue("c.m.m.MyListener1", "c.m.m.MyListener2"));

    assertThat(contentNormalizingNewLines(factoriesFile)).isEqualTo(
        """
        o.s.c.ApplicationListener=c.m.m.MyListener1,c.m.m.MyListener2
        """
      );
  }

  @Test
  void shouldAppendPropertyToFileWithProperties() {
    Path factoriesFile = Path.of(tmpDirForTest(), "src/test/resources/META-INF/spring.factories");
    loadDefaultProperties(EXISTING_SPRING_FACTORIES, factoriesFile);
    PropertiesFileSpringFactoriesHandler handler = new PropertiesFileSpringFactoriesHandler(factoriesFile);

    handler.append(propertyKey("o.s.c.ApplicationListener"), propertyValue("c.m.m.MyListener1", "c.m.m.MyListener2"));

    assertThat(contentNormalizingNewLines(factoriesFile)).isEqualTo(
        """
        org.springframework.test.context.ContextCustomizerFactory=c.m.m.MyContextCustomizerFactory
        o.s.c.ApplicationListener=c.m.m.MyListener1,c.m.m.MyListener2
        """
      );
  }

  @Test
  void shouldAppendToExistingProperty() {
    Path factoriesFile = Path.of(tmpDirForTest(), "src/test/resources/META-INF/spring.factories");
    loadDefaultProperties(EXISTING_SPRING_FACTORIES, factoriesFile);
    PropertiesFileSpringFactoriesHandler handler = new PropertiesFileSpringFactoriesHandler(factoriesFile);

    handler.append(
      propertyKey("org.springframework.test.context.ContextCustomizerFactory"),
      propertyValue("c.m.m.MyFactory", "c.m.m.MyFactory2")
    );

    assertThat(contentNormalizingNewLines(factoriesFile)).isEqualTo(
        """
        org.springframework.test.context.ContextCustomizerFactory=c.m.m.MyContextCustomizerFactory,c.m.m.MyFactory,c.m.m.MyFactory2
        """
      );
  }

  @Test
  void shouldAppendNewAndExistingProperties() {
    Path factoriesFile = Path.of(tmpDirForTest(), "src/test/resources/META-INF/spring.factories");
    loadDefaultProperties(EXISTING_SPRING_FACTORIES, factoriesFile);
    PropertiesFileSpringFactoriesHandler handler = new PropertiesFileSpringFactoriesHandler(factoriesFile);

    handler.append(
      propertyKey("org.springframework.test.context.ContextCustomizerFactory"),
      propertyValue("c.m.m.MyNewContextCustomizerFactory")
    );
    handler.append(propertyKey("o.s.c.ApplicationListener"), propertyValue("c.m.m.MyListener1"));

    assertThat(contentNormalizingNewLines(factoriesFile)).isEqualTo(
        """
        org.springframework.test.context.ContextCustomizerFactory=c.m.m.MyContextCustomizerFactory,c.m.m.MyNewContextCustomizerFactory
        o.s.c.ApplicationListener=c.m.m.MyListener1
        """
      );
  }

  @Test
  void shouldNotAppendExistingValue() {
    Path factoriesFile = Path.of(tmpDirForTest(), "src/test/resources/META-INF/spring.factories");
    PropertiesFileSpringFactoriesHandler handler = new PropertiesFileSpringFactoriesHandler(factoriesFile);

    handler.append(
      propertyKey("org.springframework.test.context.ContextCustomizerFactory"),
      propertyValue("c.m.m.MyContextCustomizerFactory")
    );

    handler.append(
      propertyKey("org.springframework.test.context.ContextCustomizerFactory"),
      propertyValue("c.m.m.MyContextCustomizerFactory")
    );

    assertThat(contentNormalizingNewLines(factoriesFile)).isEqualTo(
        """
        org.springframework.test.context.ContextCustomizerFactory=c.m.m.MyContextCustomizerFactory
        """
      );
  }
}
