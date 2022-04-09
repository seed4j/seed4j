package tech.jhipster.lite.generator.server.springboot.springcloud.consul.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;

import java.util.List;
import tech.jhipster.lite.generator.tools.domain.Project;

public class ConsulAssert {

  public static void assertDependencies(Project project) {
    assertFileContent(project, "pom.xml", "<spring-cloud.version>");

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.cloud</groupId>",
        "<artifactId>spring-cloud-dependencies</artifactId>",
        "<version>${spring-cloud.version}</version>",
        "<scope>import</scope>",
        "<type>pom</type>",
        "</dependency>"
      )
    );
    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.cloud</groupId>",
        "<artifactId>spring-cloud-starter-bootstrap</artifactId>",
        "</dependency>"
      )
    );
    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.cloud</groupId>",
        "<artifactId>spring-cloud-starter-consul-discovery</artifactId>",
        "</dependency>"
      )
    );
    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.cloud</groupId>",
        "<artifactId>spring-cloud-starter-consul-config</artifactId>",
        "</dependency>"
      )
    );
  }

  public static void assertProperties(Project project) {
    assertFileExist(project, MAIN_RESOURCES, "config/bootstrap.properties");
    assertFileExist(project, MAIN_RESOURCES, "config/bootstrap-local.properties");
    assertFileExist(project, TEST_RESOURCES, "config/bootstrap.properties");

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/bootstrap.properties"),
      List.of(
        "spring.cloud.consul.discovery.health-check-path=${server.servlet.context-path:}/management/health",
        "spring.cloud.consul.discovery.tags[0]=version=@project.version@",
        "spring.cloud.consul.discovery.tags[1]=context-path=${server.servlet.context-path:}",
        "spring.cloud.consul.discovery.tags[2]=profile=${spring.profiles.active:}",
        "spring.cloud.consul.discovery.tags[3]=git-version=${git.commit.id.describe:}",
        "spring.cloud.consul.discovery.tags[4]=git-commit=${git.commit.id.abbrev:}",
        "spring.cloud.consul.discovery.tags[5]=git-branch=${git.branch:}"
      )
    );

    String baseName = project.getBaseName().orElseThrow();
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/bootstrap.properties"),
      List.of(
        "spring.cloud.consul.discovery.instance-id=" + baseName.toLowerCase() + ":${spring.application.instance-id:${random.value}}",
        "spring.cloud.consul.discovery.service-name=" + baseName.toLowerCase(),
        "spring.cloud.consul.discovery.prefer-ip-address=true"
      )
    );

    assertFileContent(project, getPath(MAIN_RESOURCES, "config/bootstrap-local.properties"), "spring.cloud.consul.config.fail-local=false");

    assertFileContent(project, getPath(TEST_RESOURCES, "config/bootstrap.properties"), "spring.cloud.consul.enabled=false");
  }

  public static void assertDockerConsul(Project project) {
    assertFileExist(project, "src/main/docker/consul.yml");
    assertFileContent(project, "src/main/docker/consul.yml", "consul:1.11.4");
    assertFileContent(project, "src/main/docker/consul.yml", "jhipster/consul-config-loader:v0.4.1");
    assertFileExist(project, "src/main/docker/central-server-config/application.yml");
  }
}
