package tech.jhipster.lite.generator.client.vue.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import java.util.List;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.project.domain.Project;

public class VueJwt {

  private VueJwt() {}

  public static final String PACKAGE_JSON = "package.json";

  public static boolean isPiniaNotImplemented(Project project) {
    return !FileUtils.containsLines(getPath(project.getFolder(), PACKAGE_JSON), List.of("\"pinia\":"));
  }

  public static List<String> primaryLoginFiles() {
    return List.of("index.ts", "Login.component.ts", "Login.html", "Login.vue");
  }
}
