package tech.jhipster.lite.generator.client.vue.security.jwt.application;

import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class VueJwtApplicationServiceIT {

  @Autowired
  VueJwtApplicationService vueJwtApplicationService;

  @Test
  void shouldAddVueJwt() {
    Project project = tmpProjectWithPackageJsonPinia();

    vueJwtApplicationService.addJWT(project);
    assertFileExist(project, "src/main/webapp/app/common/domain/Login.ts");
    assertFileExist(project, "src/main/webapp/app/common/primary/login/index.ts");
    assertFileExist(project, "src/main/webapp/app/common/primary/login/Login.component.ts");
    assertFileExist(project, "src/main/webapp/app/common/primary/login/Login.html");
    assertFileExist(project, "src/main/webapp/app/common/primary/login/Login.vue");
    assertFileExist(project, "src/main/webapp/app/common/secondary/LoginDTO.ts");
  }
}
