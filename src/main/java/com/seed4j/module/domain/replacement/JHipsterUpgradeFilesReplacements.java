package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public record JHipsterUpgradeFilesReplacements(Collection<JHipsterUpgradeFilesReplacement> replacements) {
  public JHipsterUpgradeFilesReplacements(Collection<JHipsterUpgradeFilesReplacement> replacements) {
    this.replacements = SeedCollections.immutable(replacements);
  }

  public Stream<ContentReplacer> toContentReplacers(JHipsterProjectFolder folder, GeneratedProjectRepository generatedProject) {
    Assert.notNull("folder", folder);
    Assert.notNull("generatedProject", generatedProject);

    return replacements().stream().flatMap(toReplacer(folder, generatedProject));
  }

  private Function<JHipsterUpgradeFilesReplacement, Stream<ContentReplacer>> toReplacer(
    JHipsterProjectFolder folder,
    GeneratedProjectRepository generatedProject
  ) {
    return replacement ->
      generatedProject
        .list(folder, replacement.files())
        .stream()
        .map(file -> new OptionalFileReplacer(file, new OptionalReplacer(replacement.replacer(), replacement.replacement())));
  }
}
