package com.seed4j.project.domain.download;

import com.seed4j.shared.error.domain.Assert;
import java.util.Locale;

public record ProjectName(String name) {
  public static final ProjectName DEFAULT = new ProjectName("seed4j-application");

  public ProjectName(String name) {
    this.name = format(name);

    Assert.notBlank("name", this.name);
  }

  private String format(String name) {
    Assert.notBlank("name", name);

    return name.toLowerCase(Locale.ROOT).replace(' ', '-').replaceAll("[^0-9a-z-]", "").trim();
  }

  public String filename() {
    return name() + ".zip";
  }
}
