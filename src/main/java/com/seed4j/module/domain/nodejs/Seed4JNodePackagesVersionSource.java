package com.seed4j.module.domain.nodejs;

/**
 * {@link NodePackagesVersionSource} that are handled by Seed4J.
 */
public enum Seed4JNodePackagesVersionSource implements NodePackagesVersionSourceFactory {
  COMMON("common"),
  ANGULAR("angular"),
  REACT("react"),
  SVELTE("svelte"),
  VUE("vue");

  private final String source;

  Seed4JNodePackagesVersionSource(String source) {
    this.source = source;
  }

  @Override
  public NodePackagesVersionSource build() {
    return new NodePackagesVersionSource(source);
  }
}
