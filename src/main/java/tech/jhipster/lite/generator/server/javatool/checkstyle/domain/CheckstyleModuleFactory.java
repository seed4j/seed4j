package tech.jhipster.lite.generator.server.javatool.checkstyle.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class CheckstyleModuleFactory {

  private static final JHipsterSource TEMPLATES_SOURCE = from("server/javatool/checkstyle/main");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .javaBuildPlugins()
      .plugin(checkstylePlugin())
      .and()
      .files()
      .add(TEMPLATES_SOURCE.template("checkstyle.xml"), to("checkstyle.xml"))
      .and()
      .build();
  }

  private JavaBuildPlugin checkstylePlugin() {
    return javaBuildPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-checkstyle-plugin")
      .versionSlug("maven-checkstyle-plugin")
      .additionalElements(
        """
          <configuration>
            <configLocation>checkstyle.xml</configLocation>
            <includeTestSourceDirectory>true</includeTestSourceDirectory>
            <consoleOutput>true</consoleOutput>
            <failsOnError>true</failsOnError>
          </configuration>
          <executions>
            <execution>
              <id>validate</id>
              <phase>validate</phase>
              <goals>
                <goal>check</goal>
              </goals>
            </execution>
          </executions>
        """
      )
      .build();
  }
}
