package com.seed4j.module.domain.docker;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DockerImageVersions {

  public static final DockerImageVersions EMPTY = new DockerImageVersions(List.of());

  private final Map<DockerImageName, DockerImageVersion> versions;

  public DockerImageVersions(Collection<DockerImageVersion> versions) {
    Assert.field("versions", versions).notNull().noNullElement();

    this.versions = versions.stream().collect(Collectors.toUnmodifiableMap(DockerImageVersion::name, Function.identity()));
  }

  private DockerImageVersions(Map<DockerImageName, DockerImageVersion> versions) {
    this.versions = Collections.unmodifiableMap(versions);
  }

  public DockerImageVersion get(DockerImageName imageName) {
    return Optional.ofNullable(versions.get(imageName)).orElseThrow(() -> new UnknownDockerImageException(imageName));
  }

  public DockerImageVersions merge(DockerImageVersions other) {
    Assert.notNull("other", other);

    Map<DockerImageName, DockerImageVersion> mergedVersions = new HashMap<>(other.versions);
    mergedVersions.putAll(versions);

    return new DockerImageVersions(mergedVersions);
  }
}
