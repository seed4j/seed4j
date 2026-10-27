package com.seed4j.module.domain;

public interface AppliedModulesHistory {
  /**
   * Record an applied module in the generated project history
   *
   * @param moduleApplied
   *          the module that has just been applied
   */
  void append(Seed4JModuleApplied moduleApplied);
}
