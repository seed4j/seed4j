import { shallowMount, VueWrapper } from '@vue/test-utils';
import { LoginVue } from '@/common/primary/login';
import { createTestingPinia } from '@pinia/testing';
import { AuthenticationService } from '@/common/domain/AuthenticationService';
import { stubAuthenticationService } from '../../domain/AuthenticationService.fixture';
import { stubLogger } from '../../domain/Logger.fixture';
import { Logger } from '@/common/domain/Logger';
import { Login } from '@/common/domain/Login';
import sinon from 'sinon';

let wrapper: VueWrapper;
const $route = { path: {} };
const router = { push: sinon.stub() };

interface WrapperOptions {
  authenticationService: AuthenticationService;
  logger: Logger;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { authenticationService, logger }: WrapperOptions = {
    authenticationService: stubAuthenticationService(),
    logger: stubLogger(),
    ...wrapperOptions,
  };

  wrapper = shallowMount(LoginVue, {
    global: {
      provide: {
        authenticationService,
        logger,
        router,
      },
      plugins: [createTestingPinia()],
    },
  });
};

const fillFullForm = async (login: Login): Promise<void> => {
  const usernameInput = wrapper.find('#username');
  await usernameInput.setValue(login.username);
  const passwordInput = wrapper.find('#password');
  await passwordInput.setValue(login.password);
};

describe('Login', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should login', async () => {
    const authenticationService = stubAuthenticationService();
    authenticationService.login.resolves({});
    await wrap({ authenticationService });

    const login: Login = { username: 'admin', password: 'admin', rememberMe: true };
    await fillFullForm(login);

    const submitButton = wrapper.find('#submit');
    await submitButton.trigger('submit');

    const args = authenticationService.login.getCall(0).args[0];

    expect(args).toEqual({ username: 'admin', password: 'admin', rememberMe: false });

    // @ts-ignore
    expect(wrapper.vm.getError()).toBeFalsy();
  });

  it('Should log an error when login fails', async () => {
    const authenticationService = stubAuthenticationService();
    const logger = stubLogger();
    authenticationService.login.rejects({});
    await wrap({ authenticationService, logger });

    const login: Login = { username: 'admin', password: 'wrong_password', rememberMe: true };
    await fillFullForm(login);

    const submitButton = wrapper.find('#submit');
    await submitButton.trigger('submit');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Wrong credentials have been provided');
  });
});
