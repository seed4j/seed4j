package tech.jhipster.lite.generator.client.vue.security.jwt.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.vue.core.application.VueApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class VueJwtApplicationServiceIT {

  @Autowired
  VueJwtApplicationService vueJwtApplicationService;

  @Autowired
  VueApplicationService vueApplicationService;

  @Test
  void shouldAddVueJwt() {
    Project project = tmpProjectWithPackageJsonPinia();
    vueApplicationService.addVue(project);
    vueApplicationService.addPinia(project);
    vueJwtApplicationService.addJWT(project);
    String COMMON = MAIN_WEBAPP + "/app/common";
    assertFileExist(project, COMMON + "/domain/Login.ts");
    assertFileExist(project, COMMON + "/primary/login/index.ts");
    assertFileExist(project, COMMON + "/primary/login/Login.component.ts");
    assertFileExist(project, COMMON + "/primary/login/Login.html");
    assertFileExist(project, COMMON + "/primary/login/Login.vue");
    assertFileExist(project, COMMON + "/secondary/restLogin.ts");
    assertFileExist(project, COMMON + "/domain/AuthenticationService.ts");
    assertFileExist(project, COMMON + "/domain/JWTStoreService.ts");
  }
}
