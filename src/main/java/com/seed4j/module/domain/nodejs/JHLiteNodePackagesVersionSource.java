package com.seed4j.module.domain.nodejs;

/**
 * {@link NodePackagesVersionSource} that are handled by JHipster Lite.
 */
public enum JHLiteNodePackagesVersionSource implements NodePackagesVersionSourceFactory {
  COMMON("common"),
  ANGULAR("angular"),
  REACT("react"),
  SVELTE("svelte"),
  VUE("vue");

  private final String source;

  JHLiteNodePackagesVersionSource(String source) {
    this.source = source;
  }

  @Override
  public NodePackagesVersionSource build() {
    return new NodePackagesVersionSource(source);
  }
}
