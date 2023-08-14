package tech.jhipster.lite.project.domain.download;

import tech.jhipster.lite.shared.error.domain.Assert;

public record ProjectName(String name) {
  public static final ProjectName DEFAULT = new ProjectName("jhipster-lite-application");

  public ProjectName(String name) {
    this.name = format(name);

    Assert.notBlank("name", this.name);
  }

  private String format(String name) {
    Assert.notBlank("name", name);

    return name.toLowerCase().replace(' ', '-').replaceAll("[^0-9a-z-]", "").trim();
  }

  public String filename() {
    return name() + ".zip";
  }
}
