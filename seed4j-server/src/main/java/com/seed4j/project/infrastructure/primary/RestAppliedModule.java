package com.seed4j.project.infrastructure.primary;

import com.seed4j.project.domain.history.ProjectAction;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(name = "appliedModule", description = "Information for an applied module")
final class RestAppliedModule {

  private final String slug;

  private RestAppliedModule(String slug) {
    this.slug = slug;
  }

  static RestAppliedModule from(ProjectAction action) {
    return new RestAppliedModule(action.module().get());
  }

  @Schema(description = "Slug of the applied module", requiredMode = RequiredMode.REQUIRED)
  public String getSlug() {
    return slug;
  }
}
