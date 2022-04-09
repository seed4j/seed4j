package tech.jhipster.lite.generator.ci.github.actions.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.ci.github.actions.application.GitHubActionsApplicationService;
import tech.jhipster.lite.generator.tools.domain.GeneratorAction;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/developer-tools/github-actions/")
@Tag(name = "Continuous Integration")
class GitHubActionsResource {

  private final GitHubActionsApplicationService gitHubActionsApplicationService;

  GitHubActionsResource(GitHubActionsApplicationService gitHubActionsApplicationService) {
    this.gitHubActionsApplicationService = gitHubActionsApplicationService;
  }

  @Operation(summary = "Add GitHub Actions")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding GitHub Actions")
  @PostMapping("/maven")
  @GeneratorStep(id = GeneratorAction.GITHUB_ACTIONS)
  public void addGitHubActionsForMaven(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    gitHubActionsApplicationService.addGitHubActionsForMaven(project);
  }
}
