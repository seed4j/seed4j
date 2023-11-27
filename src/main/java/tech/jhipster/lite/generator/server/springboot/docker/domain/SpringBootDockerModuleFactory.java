package tech.jhipster.lite.generator.server.springboot.docker.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SpringBootDockerModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/docker");
  private static final JHipsterSource JIB_SOURCE = SOURCE.append("jib");

  public JHipsterModule buildJibModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("mainClass", mainClassName(properties))
        .and()
      .javaBuildPlugins()
        .plugin(jibPlugin(properties))
        .and()
      .files()
        .add(JIB_SOURCE.template("entrypoint.sh"), to("src/main/docker/jib").append("entrypoint.sh"))
        .and()
      .build();
    //@formatter:on
  }

  private String mainClassName(JHipsterModuleProperties properties) {
    return new StringBuilder()
      .append(properties.basePackage().get())
      .append(".")
      .append(properties.projectBaseName().capitalized())
      .append("App")
      .toString();
  }

  private JavaBuildPlugin jibPlugin(JHipsterModuleProperties properties) {
    return javaBuildPlugin()
      .groupId("com.google.cloud.tools")
      .artifactId("jib-maven-plugin")
      .versionSlug("jib-maven-plugin")
      .additionalElements(jibPluginAdditionalElements(properties))
      .build();
  }

  private String jibPluginAdditionalElements(JHipsterModuleProperties properties) {
    return """
    <configuration>
      <from>
        <image>eclipse-temurin:21-jre-jammy</image>
        <platforms>
          <platform>
            <architecture>amd64</architecture>
            <os>linux</os>
          </platform>
        </platforms>
      </from>
      <to>
        <image>%s:latest</image>
      </to>
      <container>
        <entrypoint>
          <shell>bash</shell>
          <option>-c</option>
          <arg>/entrypoint.sh</arg>
        </entrypoint>
        <ports>
          <port>%s</port>
        </ports>
        <environment>
          <SPRING_OUTPUT_ANSI_ENABLED>ALWAYS</SPRING_OUTPUT_ANSI_ENABLED>
          <JHIPSTER_SLEEP>0</JHIPSTER_SLEEP>
        </environment>
        <creationTime>USE_CURRENT_TIMESTAMP</creationTime>
        <user>1000</user>
      </container>
      <extraDirectories>
        <paths>src/main/docker/jib</paths>
        <permissions>
          <permission>
            <file>/entrypoint.sh</file>
            <mode>755</mode>
          </permission>
        </permissions>
      </extraDirectories>
    </configuration>
    """.formatted(properties.projectBaseName().get(), properties.serverPort().get());
  }

  public JHipsterModule buildDockerFileModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties).files().add(SOURCE.template("Dockerfile"), to("Dockerfile")).and().build();
  }
}
