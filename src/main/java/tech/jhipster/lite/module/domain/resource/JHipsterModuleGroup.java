package tech.jhipster.lite.module.domain.resource;

import java.util.List;
import tech.jhipster.lite.shared.error.domain.Assert;

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
}
