package tech.jhipster.lite.generator.client.vue.security.jwt.infrastructure.primary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.init.application.InitApplicationService;

@IntegrationTest
@AutoConfigureMockMvc
class VueJwtResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  InitApplicationService initApplicationService;
}
