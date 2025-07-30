package com.seed4j.project.domain.history;

import com.seed4j.project.domain.ModuleSlug;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.util.Map;

public final class ProjectAction {

  private final ModuleSlug module;
  private final Instant date;
  private final ModuleParameters parameters;

  private ProjectAction(ProjectActionBuilder builder) {
    Assert.notNull("module", builder.module);
    Assert.notNull("date", builder.date);
    Assert.notNull("parameters", builder.parameters);

    module = builder.module;
    date = builder.date;
    parameters = builder.parameters;
  }

  public static ProjectActionModuleBuilder builder() {
    return new ProjectActionBuilder();
  }

  public ModuleSlug module() {
    return module;
  }

  public Instant date() {
    return date;
  }

  public ModuleParameters parameters() {
    return parameters;
  }

  private static final class ProjectActionBuilder
    implements ProjectActionModuleBuilder, ProjectActionDateBuilder, ProjectActionParametersBuilder {

    private ModuleSlug module;
    private Instant date;
    private ModuleParameters parameters;

    @Override
    public ProjectActionDateBuilder module(ModuleSlug module) {
      this.module = module;

      return this;
    }

    @Override
    public ProjectActionParametersBuilder date(Instant date) {
      this.date = date;

      return this;
    }

    @Override
    public ProjectAction parameters(ModuleParameters properties) {
      this.parameters = properties;

      return new ProjectAction(this);
    }
  }

  public interface ProjectActionModuleBuilder {
    ProjectActionDateBuilder module(ModuleSlug module);

    default ProjectActionDateBuilder module(String module) {
      return module(new ModuleSlug(module));
    }
  }

  public interface ProjectActionDateBuilder {
    ProjectActionParametersBuilder date(Instant date);
  }

  public interface ProjectActionParametersBuilder {
    ProjectAction parameters(ModuleParameters properties);

    default ProjectAction parameters(Map<String, Object> properties) {
      return parameters(new ModuleParameters(properties));
    }
  }
}
