package tech.jhipster.lite.generator.server.springboot.docker.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class SpringBootDockerDomainService implements SpringBootDockerService {

  public static final String SOURCE = "server/springboot/docker/jib";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;

  public SpringBootDockerDomainService(ProjectRepository projectRepository, BuildToolService buildToolService) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
  }

  @Override
  public void addJib(Project project) {
    this.addJibPlugin(project);
    this.addJibFiles(project);
  }

  @Override
  public void addJibFiles(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");
    String packageName = project.getPackageName().orElse("com.mycompany.myapp");
    String className = WordUtils.upperFirst(baseName);
    project.addConfig("mainClass", packageName + "." + className + "App");
    projectRepository.template(project, getPath(SOURCE), "entrypoint.sh", getPath("src/main/docker/jib"));
  }

  @Override
  public void addJibPlugin(Project project) {
    Plugin jibPlugin = Plugin
      .builder()
      .groupId("com.google.cloud.tools")
      .artifactId("jib-maven-plugin")
      .version("\\${jib-maven-plugin.version}")
      .additionalElements(
        """
          <configuration>
            <from>
              <image>\\${jib-maven-plugin.image}</image>
              <platforms>
                <platform>
                  <architecture>\\${jib-maven-plugin.architecture}</architecture>
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
          </configuration>""".formatted(
            project.getBaseName().orElse("jhipster"),
            project.getServerPort()
          )
      )
      .build();
    buildToolService
      .getVersion(project, "jib-maven-plugin")
      .ifPresentOrElse(
        version -> buildToolService.addProperty(project, "jib-maven-plugin.version", version),
        () -> {
          throw new GeneratorException("Version not found: jib-maven-plugin");
        }
      );
    this.buildToolService.addProperty(project, "jib-maven-plugin.image", SpringBootDocker.getDockerBaseImage());
    this.buildToolService.addProperty(project, "jib-maven-plugin.architecture", SpringBootDocker.getDockerPlatformArchitecture());
    this.buildToolService.addPlugin(project, jibPlugin);
  }
}
