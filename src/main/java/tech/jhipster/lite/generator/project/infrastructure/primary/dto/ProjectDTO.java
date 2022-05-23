package tech.jhipster.lite.generator.project.infrastructure.primary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.project.domain.Project;

@Schema(description = "Project DTO")
public class ProjectDTO {

  @JsonProperty("folder")
  @Schema(description = "folder", example = "/tmp/jhlite/jhipster", required = true)
  private String folder;

  @JsonProperty("generator-jhipster")
  @Schema(
    description = "generator-jhipster configuration",
    example = """
      {
        "baseName": "jhipster",
        "projectName": "jhipster project",
        "packageName": "com.mycompany.myapp",
        "serverPort": 8080
      }
    """,
    required = true
  )
  private Map<String, Object> generatorJhipster;

  @JsonProperty("remoteUrl")
  @Schema(description = "remoteUrl", example = "https://github.com/jhipster/jhipster-lite", required = true)
  private String remoteUrl;

  public String getFolder() {
    return folder;
  }

  public Map<String, Object> getGeneratorJhipster() {
    return generatorJhipster;
  }

  public String getRemoteUrl() {
    return remoteUrl;
  }

  public static Project toProject(ProjectDTO projectDTO) {
    Assert.notNull("projectDTO", projectDTO);

    return Project
      .builder()
      .folder(projectDTO.getFolder())
      .remoteUrl(projectDTO.getRemoteUrl())
      .config(projectDTO.getGeneratorJhipster())
      .build();
  }

  public ProjectDTO folder(String path) {
    this.folder = path;
    return this;
  }

  public ProjectDTO generatorJhipster(Map<String, Object> generatorJhipster) {
    this.generatorJhipster = generatorJhipster;
    return this;
  }

  public ProjectDTO remoteUrl(String remoteUrl) {
    this.remoteUrl = remoteUrl;
    return this;
  }
}
