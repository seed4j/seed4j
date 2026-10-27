package com.seed4j.project.infrastructure.secondary;

import com.seed4j.project.domain.history.ProjectAction;
import com.seed4j.project.domain.history.ProjectHistory;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

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
