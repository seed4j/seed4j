package com.seed4j.module.domain.resource;

/**
 * Represents the maturity and adoption level of Seed4J modules.
 * Ranks range from experimental to community-validated,
 * helping users assess module stability and production readiness.
 */
public enum Seed4JModuleRank {
  /**
   * Experimental or advanced module requiring specific expertise
   */
  RANK_D,

  /**
   * Module without known production usage
   */
  RANK_C,

  /**
   * Module with at least one confirmed production usage
   */
  RANK_B,

  /**
   * Module with multiple production usages across different projects
   * and documented through talks, books or blog posts
   */
  RANK_A,

  /**
   * Production-proven module providing unique features,
   * validated by community feedback (10+ endorsements)
   */
  RANK_S,
}
