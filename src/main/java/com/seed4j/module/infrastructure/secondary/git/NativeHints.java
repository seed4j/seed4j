package com.seed4j.module.infrastructure.secondary.git;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.CommitConfig;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.util.sha1.SHA1;
import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native runtime hints")
class NativeHints implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
    /*
    Mimics quarkus native configuration. See https://github.com/quarkiverse/quarkus-jgit/blob/main/deployment/src/main/java/io/quarkus/jgit/deployment/JGitProcessor.java
    Their calls to
    ReflectiveClassBuildItem(true, true,
                "org.eclipse.jgit.api.MergeCommand$FastForwardMode",

    means methods, fields and constructors
     */
    hints
      .reflection()
      .registerType(MergeCommand.ConflictStyle.class, MemberCategory.values())
      .registerType(MergeCommand.FastForwardMode.class, MemberCategory.values())
      .registerType(MergeCommand.FastForwardMode.Merge.class, MemberCategory.values())
      .registerType(DiffAlgorithm.SupportedAlgorithm.class, MemberCategory.values())
      .registerType(JGitText.class, MemberCategory.values())
      .registerType(CommitConfig.CleanupMode.class, MemberCategory.values())
      .registerType(SHA1.Sha1Implementation.class, MemberCategory.values())
      .registerType(CoreConfig.AutoCRLF.class, MemberCategory.values())
      .registerType(CoreConfig.CheckStat.class, MemberCategory.values())
      .registerType(CoreConfig.EOL.class, MemberCategory.values())
      .registerType(CoreConfig.EolStreamType.class, MemberCategory.values())
      .registerType(CoreConfig.HideDotFiles.class, MemberCategory.values())
      .registerType(CoreConfig.SymLinks.class, MemberCategory.values())
      .registerType(CoreConfig.LogRefUpdates.class, MemberCategory.values())
      .registerType(CoreConfig.TrustStat.class, MemberCategory.values());

    // patterns for JGit and our own template files
    hints.resources().registerPattern("generator/**").registerResourceBundle("org.eclipse.jgit.internal.JGitText.properties");
  }
}
