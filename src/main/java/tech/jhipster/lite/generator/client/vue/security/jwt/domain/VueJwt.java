package tech.jhipster.lite.generator.client.vue.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.project.domain.Project;

public class VueJwt {

  private VueJwt() {}

  public static final Collection<String> MAIN_PROVIDES = List.of(
    "const authenticationRepository = new AuthenticationRepository(axiosHttp, pinia);",
    "app.provide('authenticationService', authenticationRepository);",
    "app.provide('logger', consoleLogger);",
    "app.provide('router', router);"
  );
  public static final Collection<String> MAIN_PROVIDER = List.of(
    "const axiosHttp = new AxiosHttp(axios.create({ baseURL: '' }));",
    "const consoleLogger = new ConsoleLogger(console);"
  );
  public static final Collection<String> MAIN_IMPORTS = List.of(
    "import AuthenticationRepository from './common/secondary/AuthenticationRepository';",
    "import { AxiosHttp } from './http/AxiosHttp';",
    "import axios from 'axios';",
    "import ConsoleLogger from './common/secondary/ConsoleLogger';",
    "import Homepage from './common/primary/homepage/Homepage.vue';"
  );

  public static boolean isPiniaNotImplemented(Project project) {
    return !FileUtils.containsLines(getPath(project.getFolder(), PACKAGE_JSON), List.of("\"pinia\":"));
  }

  public static List<String> primaryLoginFiles() {
    return List.of("index.ts", "Login.component.ts", "Login.html", "Login.vue");
  }

  public static final Collection<String> LOGIN_ROUTES = List.of(
    "\t{",
    "\tpath: '/login',",
    "\tname: 'Login',",
    "\tcomponent: LoginVue,",
    "\t},",
    "\t{",
    "\tpath: '/',",
    "\tname: 'Homepage',",
    "\tcomponent: AppVue,",
    "\t},"
  );

  public static final Collection<String> ROUTER_IMPORTS = List.of("import { LoginVue } from '@/common/primary/login';");

  public static Collection<String> primaryHomepageFiles() {
    return List.of("index.ts", "Homepage.component.ts", "Homepage.html", "Homepage.vue");
  }

  public static Collection<String> domainFiles() {
    return List.of("AuthenticationService.ts", "JWTStoreService.ts", "Login.ts", "User.ts");
  }

  public static Collection<String> secondaryFiles() {
    return List.of("AuthenticationRepository.ts", "UserDTO.ts");
  }

  public static Collection<String> testDomainFiles() {
    return List.of("AuthenticationService.fixture.ts", "JWTStoreService.spec.ts");
  }

  public static Collection<String> testSecondaryFiles() {
    return List.of("AuthenticationRepository.spec.ts", "restLogin.spec.ts", "UserDTO.spec.ts");
  }

  public static Map<String, String> appComponent() {
    return Map.of(
      "import { AuthenticationService } from '@/common/domain/AuthenticationService';\n".concat(
          "import { Logger } from '@/common/domain/Logger';\n"
        )
        .concat("import { User } from '@/common/domain/User';\n")
        .concat("import { Router } from 'vue-router';\n")
        .concat("import { jwtStore } from '@/common/domain/JWTStoreService';\n")
        .concat("import { inject, ref } from \"vue\";"),
      "import",
      "const authenticationService = inject('authenticationService') as AuthenticationService;\n".concat(
          "\tconst logger = inject('logger') as Logger;\n"
        )
        .concat("\tconst router = inject('router') as Router;\n")
        .concat("\n")
        .concat("\tlet store = jwtStore();\n")
        .concat("\tlet isAuthenticated:boolean = store.isAuth;\n")
        .concat("\tlet user = ref<User>({\n")
        .concat("\t\tusername: '',\n")
        .concat("\t\tauthorities: [''],\n")
        .concat("\t});\n")
        .concat("\n")
        .concat("\tconst onConnect = async (): Promise<void> => {\n")
        .concat("\t\tawait authenticationService\n")
        .concat("\t\t.authenticate()\n")
        .concat("\t\t.then(response => {\n")
        .concat("\t\t\tuser.value = response;\n")
        .concat("\t\t})\n")
        .concat("\t\t.catch(error => {\n")
        .concat("\t\t\tlogger.error('The token provided is not know by our service', error);\n")
        .concat("\t\t});\n")
        .concat("\t}\n")
        .concat("\n")
        .concat("\tconst onLogout = async (): Promise<void> => {\n")
        .concat("\t\tawait authenticationService\n")
        .concat("\t\t.logout();\n")
        .concat("\t\trouter.push(\"/login\");\n")
        .concat("\t};\n"),
      "setup",
      "user,\n".concat("\tisAuthenticated,\n").concat("\tonConnect,\n").concat("\tonLogout,\n"),
      "return"
    );
  }

  public static List<String> appHTML() {
    return List.of(
      "<div id=\"jwt-authentication\">",
      "\t<div v-if=\"isAuthenticated\">",
      "\t\t<p>You are connected as </p>",
      "\t\t<div v-if=\"user.username == ''\">",
      "\t\t\t<button id=\"identify\" @click.prevent=\"onConnect\">click to see</button>",
      "\t\t</div>",
      "\t\t<div v-else>",
      "\t\t\t<p>{{user.username}}</p>",
      "\t\t\t<button id=\"logout\" @click.prevent=\"onLogout\">click to logout</button>",
      "\t\t</div>",
      "\t</div>",
      "\t<div v-else>",
      "\t\t<p>You are not connected</p>",
      "\t\t<router-link to=\"/login\">Login</router-link>",
      "\t</div>",
      "</div>"
    );
  }
}
