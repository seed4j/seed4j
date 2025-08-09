package com.seed4j.module.domain.nodejs;

/**
 * {@link NodePackagesVersionSource} that are handled by Seed4J.
 */
public enum SeedNodePackagesVersionSource implements NodePackagesVersionSourceFactory {
  COMMON("common"),
  ANGULAR("angular"),
  REACT("react"),
  SVELTE("svelte"),
  VUE("vue");

  private final String source;

  SeedNodePackagesVersionSource(String source) {
    this.source = source;
  }

  @Override
  public NodePackagesVersionSource build() {
    return new NodePackagesVersionSource(source);
  }
}
