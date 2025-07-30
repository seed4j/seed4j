package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;
import java.util.List;

public record JHipsterModuleGroup(String group) {
  public JHipsterModuleGroup {
    Assert.field("group", group).notBlank().maxLength(50);
  }

  public String get() {
    return group();
  }

  public List<String> list() {
    return List.of(group());
  }

  @Override
  public String toString() {
    return group();
  }
}
