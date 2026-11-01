package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public record Seed4JUpgradeFilesReplacements(Collection<Seed4JUpgradeFilesReplacement> replacements) {
  public Seed4JUpgradeFilesReplacements(Collection<Seed4JUpgradeFilesReplacement> replacements) {
    Assert.field("replacements", replacements).notNull().noNullElement();

    this.replacements = List.copyOf(replacements);
  }

  public Stream<ContentReplacer> toContentReplacers(Seed4JProjectFolder folder, GeneratedProjectRepository generatedProject) {
    Assert.notNull("folder", folder);
    Assert.notNull("generatedProject", generatedProject);

    return replacements().stream().flatMap(toReplacer(folder, generatedProject));
  }

  private Function<Seed4JUpgradeFilesReplacement, Stream<ContentReplacer>> toReplacer(
    Seed4JProjectFolder folder,
    GeneratedProjectRepository generatedProject
  ) {
    return replacement ->
      generatedProject
        .list(folder, replacement.files())
        .stream()
        .map(file -> new OptionalFileReplacer(file, new OptionalReplacer(replacement.replacer(), replacement.replacement())));
  }
}
