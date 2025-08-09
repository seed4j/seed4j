package com.seed4j.module.domain.mavenplugin;

import static org.apache.commons.lang3.builder.ToStringStyle.*;

import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class MavenPluginExecution {

  private final Collection<MavenPluginExecutionGoal> goals;
  private final Optional<MavenPluginExecutionId> id;
  private final Optional<MavenBuildPhase> phase;
  private final Optional<MavenPluginConfiguration> configuration;

  private MavenPluginExecution(MavenPluginExecutionBuilder builder) {
    Assert.notEmpty("goals", builder.goals);
    this.goals = SeedCollections.immutable(builder.goals);
    this.id = Optional.ofNullable(builder.id);
    this.phase = Optional.ofNullable(builder.phase);
    this.configuration = Optional.ofNullable(builder.configuration);
  }

  public static MavenPluginExecutionGoalsBuilder builder() {
    return new MavenPluginExecutionBuilder();
  }

  public Collection<MavenPluginExecutionGoal> goals() {
    return goals;
  }

  public Optional<MavenPluginExecutionId> id() {
    return id;
  }

  public Optional<MavenBuildPhase> phase() {
    return phase;
  }

  public Optional<MavenPluginConfiguration> configuration() {
    return configuration;
  }

  private static final class MavenPluginExecutionBuilder implements MavenPluginExecutionGoalsBuilder, MavenPluginExecutionOptionalBuilder {

    private MavenPluginExecutionId id;
    private MavenBuildPhase phase;
    private List<MavenPluginExecutionGoal> goals;
    private MavenPluginConfiguration configuration;

    @Override
    public MavenPluginExecutionOptionalBuilder goals(MavenPluginExecutionGoal... goals) {
      this.goals = List.of(goals);
      return this;
    }

    @Override
    public MavenPluginExecutionOptionalBuilder phase(MavenBuildPhase phase) {
      this.phase = phase;
      return this;
    }

    @Override
    public MavenPluginExecutionOptionalBuilder id(MavenPluginExecutionId executionId) {
      this.id = executionId;
      return this;
    }

    @Override
    public MavenPluginExecutionOptionalBuilder configuration(MavenPluginConfiguration configuration) {
      this.configuration = configuration;
      return this;
    }

    @Override
    public MavenPluginExecution build() {
      return new MavenPluginExecution(this);
    }
  }

  public interface MavenPluginExecutionGoalsBuilder {
    MavenPluginExecutionOptionalBuilder goals(MavenPluginExecutionGoal... goals);

    default MavenPluginExecutionOptionalBuilder goals(String... goals) {
      return goals(Stream.of(goals).map(MavenPluginExecutionGoal::new).toArray(MavenPluginExecutionGoal[]::new));
    }
  }

  public interface MavenPluginExecutionOptionalBuilder {
    MavenPluginExecutionOptionalBuilder phase(MavenBuildPhase phase);

    MavenPluginExecutionOptionalBuilder id(MavenPluginExecutionId phase);

    default MavenPluginExecutionOptionalBuilder id(String executionId) {
      return id(new MavenPluginExecutionId(executionId));
    }

    MavenPluginExecutionOptionalBuilder configuration(MavenPluginConfiguration configuration);

    default MavenPluginExecutionOptionalBuilder configuration(String configuration) {
      return configuration(new MavenPluginConfiguration(configuration));
    }

    MavenPluginExecution build();
  }

  @Override
  @SuppressWarnings("java:S1192")
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, SHORT_PREFIX_STYLE)
      .append("goals", goals)
      .append("id", id.map(MavenPluginExecutionId::toString).orElse(""))
      .append("phase", phase.map(MavenBuildPhase::toString).orElse(""))
      .append("configuration", configuration.map(MavenPluginConfiguration::toString).orElse(""));
    return builder.toString();
  }
}
