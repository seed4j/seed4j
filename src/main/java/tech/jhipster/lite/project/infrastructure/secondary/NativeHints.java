package tech.jhipster.lite.project.infrastructure.secondary;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import tech.jhipster.lite.project.domain.history.ProjectAction;
import tech.jhipster.lite.project.domain.history.ProjectHistory;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native runtime hints")
class NativeHints implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
    hints
      .reflection()
      .registerType(PersistedProjectHistory.class, MemberCategory.values())
      .registerType(ProjectHistory.class, MemberCategory.values())
      .registerType(ProjectAction.class, MemberCategory.values())
      .registerType(PersistedProjectAction.class, MemberCategory.values());
  }
}
