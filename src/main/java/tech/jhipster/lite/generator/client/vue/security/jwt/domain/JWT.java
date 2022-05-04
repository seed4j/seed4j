package tech.jhipster.lite.generator.client.vue.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import java.util.List;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.project.domain.Project;

public class JWT {

  private JWT() {}

  public static final String PACKAGE_JSON = "package.json";

  public static boolean checkIfPinia(Project project) {
    return FileUtils.containsLines(getPath(project.getFolder(), PACKAGE_JSON), List.of("\"pinia\":"));
  }
}
