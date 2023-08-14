package tech.jhipster.lite.module.domain.replacement;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.GeneratedProjectRepository;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterUpgradeFilesReplacements(Collection<JHipsterUpgradeFilesReplacement> replacements) {
  public JHipsterUpgradeFilesReplacements(Collection<JHipsterUpgradeFilesReplacement> replacements) {
    this.replacements = JHipsterCollections.immutable(replacements);
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
