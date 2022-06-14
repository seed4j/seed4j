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
    "{",
    "    path: '/login',",
    "    name: 'Login',",
    "     component: LoginVue,",
    "  },"
  );

  public static final Collection<String> ROUTER_IMPORTS = List.of("import { LoginVue } from '@/common/primary/login';");

  public static Collection<String> domainFiles() {
    return List.of("AuthenticationService.ts", "Login.ts", "User.ts");
  }

  public static Collection<String> secondaryFiles() {
    return List.of("AuthenticationRepository.ts", "UserDTO.ts", "JwtStoreService.ts");
  }

  public static Collection<String> testDomainFiles() {
    return List.of("AuthenticationService.fixture.ts");
  }

  public static Collection<String> testSecondaryFiles() {
    return List.of("AuthenticationRepository.spec.ts", "RestLogin.spec.ts", "UserDTO.spec.ts", "JwtStoreService.spec.ts");
  }

  public static Map<String, String> appComponent() {
    return Map.of(
      """
      import { AuthenticationService } from '@/common/domain/AuthenticationService';
      import { Logger } from '@/common/domain/Logger';
      import { User } from '@/common/domain/User';
      import { Router } from 'vue-router';
      import { jwtStore } from '@/common/secondary/JwtStoreService';
      """,
      "import",
      """
      const authenticationService = inject('authenticationService') as AuthenticationService;
      const logger = inject('logger') as Logger;
      const router = inject('router') as Router;

      let store = jwtStore();
      let isAuthenticated:boolean = store.isAuth;
      let user = ref<User>({
        username: '',
        authorities: [''],
      });

      const onConnect = async (): Promise<void> => {
        await authenticationService
        .authenticate()
        .then(response => {
          user.value = response;
        })
        .catch(error => {
          logger.error('The token provided is not know by our service', error);
        });
      }

      const onLogout = async (): Promise<void> => {
        authenticationService
        .logout();
        router.push("/login");
      };
      """,
      "setup",
      """
        user,
        isAuthenticated,
        onConnect,
        onLogout,
      """,
      "return"
    );
  }

  public static Map<String, String> appTest() {
    return Map.of(
      """
        import { createTestingPinia } from '@pinia/testing';
        import { AuthenticationService } from '@/common/domain/AuthenticationService';
        import { stubAuthenticationService } from '../../domain/AuthenticationService.fixture';
        import { stubLogger } from '../../domain/Logger.fixture';
        import { Logger } from '@/common/domain/Logger';
        import sinon from 'sinon';
        """,
      "test-import",
      """
        const \\$route = { path: {} };
        const router = { push: sinon.stub() };
        """,
      "test-variables",
      """
          authenticationService: AuthenticationService;
          logger: Logger;
      """,
      "test-wrapper-options",
      """
          const { authenticationService, logger }: WrapperOptions = {
          authenticationService: stubAuthenticationService(),
          logger: stubLogger(),
          ...wrapperOptions,
          };
      """,
      "test-wrapper-variable",
      """
        global: {
          stubs: ['router-link'],
          provide: {
            authenticationService,
            logger,
            router,
          },
          plugins: [createTestingPinia({
             initialState: {
                JWTStore: {token: '123456789'},
             },
          })],
        },
      """,
      "test-wrapper-mount",
      """
        it('should authenticate', async () => {
          const authenticationService = stubAuthenticationService();
          const logger = stubLogger();
          authenticationService.authenticate.resolves({ username: 'username', authorities: ['admin'] });
          await wrap({ authenticationService, logger });

          const clickButton = wrapper.find('#identify');
          await clickButton.trigger('click');

        // @ts-ignore
        expect(wrapper.vm.user).toStrictEqual({ username: 'username', authorities: ['admin'] });
        });

        it('Should log an error when authentication fails', async () => {
          const authenticationService = stubAuthenticationService();
          const logger = stubLogger();
          authenticationService.authenticate.rejects({});
          await wrap({ authenticationService, logger });

          const clickButton = wrapper.find('#identify');
          await clickButton.trigger('click');

            const [message] = logger.error.getCall(0).args;
            expect(message).toBe('The token provided is not know by our service');
        });

          it('Should log out', async () => {
            const authenticationService = stubAuthenticationService();
            const logger = stubLogger();
            authenticationService.authenticate.resolves({ username: 'username', authorities: ['admin'] });
            await wrap({ authenticationService, logger });


            const clickButton = wrapper.find('#identify');
            await clickButton.trigger('click');
            const logoutButton = wrapper.find('#logout');
            await logoutButton.trigger('click');

            sinon.assert.calledOnce(authenticationService.logout);
          });
          """,
      "test-routes"
    );
  }

  public static List<String> appHTML() {
    return List.of(
      "<div id=\"jwt-authentication\">",
      "  <div v-if=\"isAuthenticated\">",
      "    <p>You are connected as </p>",
      "    <div v-if=\"user.username == ''\">",
      "      <button id=\"identify\" @click.prevent=\"onConnect\">click to see</button>",
      "    </div>",
      "    <div v-else>",
      "      <p>{{user.username}}</p>",
      "      <button id=\"logout\" @click.prevent=\"onLogout\">click to logout</button>",
      "    </div>",
      "  </div>",
      "  <div v-else>",
      "    <p>You are not connected</p>",
      "    <router-link to=\"/login\">Login</router-link>",
      "  </div>",
      "</div>"
    );
  }

  public static Map<String, String> routerspec() {
    return Map.of(
      """
      import { LoginVue } from '@/common/primary/login';
      import { createTestingPinia } from '@pinia/testing';
      import { AuthenticationService } from '@/common/domain/AuthenticationService';
      import { stubAuthenticationService } from '../common/domain/AuthenticationService.fixture';
      import { stubLogger } from '../common/domain/Logger.fixture';
      import { Logger } from '@/common/domain/Logger';
      """,
      "test-import",
      """
      authenticationService: AuthenticationService;
      logger: Logger;
      """,
      "test-wrapper-options",
      """
      const { authenticationService, logger }: WrapperOptions = {
          authenticationService: stubAuthenticationService(),
          logger: stubLogger(),
          ...wrapperOptions,
      };
      """,
      "test-wrapper-variable",
      """
        global: {
          stubs: ['router-link'],
          provide: {
            authenticationService,
            logger,
            router,
          },
          plugins: [createTestingPinia()],
        },
        """,
      "test-wrapper-mount",
      """
        it('Should go to LoginVue', async () => {
          router.push('/Login');
          await wrapper.vm.\\$nextTick();
          expect(wrapper.findComponent(LoginVue)).toBeTruthy();
        });
        afterAll(async () => new Promise(resolve => window.setTimeout(resolve, 0)));
        """,
      "test-routes"
    );
  }
}
