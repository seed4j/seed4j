# Vue Authentication Components Documentation

This document provides an overview and demonstrates the practical usage of the vue authentication module.

## File Structure

```
src/
├── main/
│   └── webapp/
│       └── app/
│           ├── auth/
│           │   ├── application/
│           │   │   └── AuthRouter.ts
│           │   └── infrastructure/
│           │       └── primary/
│           │           ├── AuthVue.component.ts
│           │           └── AuthVue.vue
│           └── shared/
│               └── http/
│                   └── infrastructure/
│                       └── secondary/
│                           └── AxiosAuthInterceptor.ts
├── test/
│   └── webapp/
│       └── unit/
│           ├── auth/
│           │   └── infrastructure/
│           │       └── primary/
│           │           └── AuthVueComponent.spec.ts
│           └── shared/
│               └── http/
│                   └── infrastructure/
│                       └── secondary/
│                           ├── AxiosAuthInterceptor.spec.ts
│                           └── AxiosStub.ts
└── router.ts
```

## Detailed File Explanations

### 1. AuthRouter.ts

Location: `src/main/webapp/app/auth/application/AuthRouter.ts`

This file defines the route for the authentication component.

```typescript
import type { RouteRecordRaw } from 'vue-router';
import AuthVue from '@/auth/infrastructure/primary/AuthVue.vue';

export const authRoutes = (): RouteRecordRaw[] => [
  {
    path: '/login',
    name: 'Login',
    component: AuthVue,
  },
];
```

### 2. AuthVue.component.ts

Location: `src/main/webapp/app/auth/infrastructure/primary/AuthVue.component.ts`

This file contains the component logic for the authentication view.

```typescript
import { defineComponent, onMounted, ref } from 'vue';
import { inject } from '@/injections';
import { AUTH_REPOSITORY } from '@/auth/application/AuthProvider';
import type { AuthenticatedUser } from '@/auth/domain/AuthenticatedUser';

export default defineComponent({
  name: 'AuthVue',
  setup() {
    const authRepository = inject(AUTH_REPOSITORY);
    const user = ref<AuthenticatedUser | null>(null);
    const isLoading = ref(true);

    onMounted(() => {
      init();
    });

    const init = () => {
      isLoading.value = true;
      authRepository
        .authenticated()
        .then(authenticated => {
          if (authenticated) {
            return authRepository.currentUser();
          } else {
            return null;
          }
        })
        .then(currentUser => {
          user.value = currentUser;
        })
        .catch(error => {
          console.error('Initialization failed:', error);
          user.value = null;
        })
        .finally(() => {
          isLoading.value = false;
        });
    };

    const login = () => {
      isLoading.value = true;
      authRepository
        .login()
        .then(() => authRepository.currentUser())
        .then(currentUser => {
          user.value = currentUser.isAuthenticated ? currentUser : null;
        })
        .catch(error => {
          console.error('Login failed:', error);
          user.value = null;
        })
        .finally(() => {
          isLoading.value = false;
        });
    };

    const logout = () => {
      isLoading.value = true;
      authRepository
        .logout()
        .then(() => {
          user.value = null;
        })
        .catch(error => {
          console.error('Logout failed:', error);
        })
        .finally(() => {
          isLoading.value = false;
        });
    };

    return {
      user,
      isLoading,
      login,
      logout,
    };
  },
});
```

### 3. AuthVue.vue

Location: `src/main/webapp/app/auth/infrastructure/primary/AuthVue.vue`

This file is the template for the authentication component.

```vue
<template>
  <div>
    <div v-if="isLoading">Loading...</div>
    <button v-else-if="!user" @click="login">Login</button>
    <div v-else>
      <p>Welcome, {{ user.username }}!</p>
      <button @click="logout">Logout</button>
    </div>
  </div>
</template>

<script lang="ts" src="./AuthVue.component.ts"></script>
```

### 4. AxiosAuthInterceptor.ts

Location: `src/main/webapp/app/shared/http/infrastructure/secondary/AxiosAuthInterceptor.ts`

This file sets up Axios interceptors to handle authentication tokens and 401 errors.

```typescript
import type { AxiosInstance, InternalAxiosRequestConfig, AxiosResponse, AxiosError } from 'axios';
import { inject } from '@/injections';
import { AUTH_REPOSITORY } from '@/auth/application/AuthProvider';

export const setupAxiosInterceptors = (axios: AxiosInstance): void => {
  const auths = inject(AUTH_REPOSITORY);

  axios.interceptors.request.use(async (config: InternalAxiosRequestConfig) => {
    if (await auths.authenticated()) {
      const token = await auths.refreshToken();
      config.headers.set('Authorization', `Bearer ${token}`);
    }
    return config;
  });

  axios.interceptors.response.use(
    (response: AxiosResponse): AxiosResponse => response,
    async (error: AxiosError): Promise<never> => {
      if (error.response && error.response.status === 401) {
        await auths.logout();
        //TODO: Redirect to login page or update application state
      }
      return Promise.reject(error);
    },
  );
};
```

### 5. router.ts

Location: `src/main/webapp/app/router.ts`

This file sets up the main router for the application, including authentication routes.

```typescript
import { createRouter, createWebHistory } from 'vue-router';
import { homeRoutes } from '@/home/application/HomeRouter';
import { authRoutes } from '@/auth/application/AuthRouter';

const routes = [...homeRoutes(), ...authRoutes()];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
```

### 6. AuthVueComponent.spec.ts

Location: `src/test/webapp/unit/auth/infrastructure/primary/AuthVueComponent.spec.ts`

This file contains unit tests for the AuthVue component.

```typescript
import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { flushPromises, mount, VueWrapper } from '@vue/test-utils';
import AuthVue from '@/auth/infrastructure/primary/AuthVue.vue';
import { AUTH_REPOSITORY } from '@/auth/application/AuthProvider';
import { provide } from '@/injections';
import sinon from 'sinon';
import type { SinonStub } from 'sinon';
import type { AuthRepository } from '@/auth/domain/AuthRepository';

interface MockAuthRepository extends AuthRepository {
  currentUser: SinonStub;
  login: SinonStub;
  logout: SinonStub;
  authenticated: SinonStub;
  refreshToken: SinonStub;
}

const mockAuthRepository: MockAuthRepository = {
  currentUser: sinon.stub(),
  login: sinon.stub(),
  logout: sinon.stub(),
  authenticated: sinon.stub(),
  refreshToken: sinon.stub(),
};

const wrap = () => {
  provide(AUTH_REPOSITORY, mockAuthRepository);
  return mount(AuthVue);
};

const componentVm = (wrapper: VueWrapper) => wrapper.findComponent(AuthVue).vm;

describe('AuthVue', () => {
  let wrapper: VueWrapper;
  let consoleErrorSpy: any;

  beforeEach(async () => {
    mockAuthRepository.authenticated.reset();
    mockAuthRepository.currentUser.reset();
    mockAuthRepository.login.reset();

    mockAuthRepository.authenticated.resolves(false);
    mockAuthRepository.currentUser.resolves({ isAuthenticated: false, username: '', token: '' });

    wrapper = wrap();
    await flushPromises();

    consoleErrorSpy = vi.spyOn(console, 'error').mockImplementation(() => {});
  });

  afterEach(() => {
    consoleErrorSpy.mockRestore();
  });

  it('should render login button when user is not authenticated', async () => {
    expect(wrapper.find('button').text()).toBe('Login');
  });

  it('should render logout button and username when user is authenticated', async () => {
    mockAuthRepository.authenticated.resolves(true);
    mockAuthRepository.currentUser.resolves({ isAuthenticated: true, username: 'test', token: 'token' });

    wrapper = wrap();
    await flushPromises();

    expect(wrapper.find('p').text()).toBe('Welcome, test!');
    expect(wrapper.find('button').text()).toBe('Logout');
  });

  describe('Login', () => {
    it('should handle failed login attempt', async () => {
      mockAuthRepository.login.rejects(new Error('Login failed'));

      await wrapper.find('button').trigger('click');
      await flushPromises();

      expect(mockAuthRepository.login.called).toBe(true);
      expect(componentVm(wrapper).isLoading).toBe(false);
      expect(componentVm(wrapper).user).toBeNull();
      expect(wrapper.find('button').text()).toBe('Login');
      expect(consoleErrorSpy).toHaveBeenCalledWith('Login failed:', expect.any(Error));
    });

    it('should handle successful login attempt', async () => {
      mockAuthRepository.login.resolves();
      mockAuthRepository.authenticated.resolves(true);
      mockAuthRepository.currentUser.resolves({ isAuthenticated: true, username: 'test', token: 'token' });

      await wrapper.find('button').trigger('click');
      await flushPromises();

      expect(mockAuthRepository.login.called).toBe(true);
      expect(componentVm(wrapper).isLoading).toBe(false);
      expect(wrapper.find('p').text()).toBe('Welcome, test!');
      expect(wrapper.find('button').text()).toBe('Logout');
    });

    it('should set isLoading to true during login process', async () => {
      mockAuthRepository.login.resolves();
      mockAuthRepository.authenticated.resolves(true);
      mockAuthRepository.currentUser.resolves({ isAuthenticated: true, username: 'test', token: 'token' });

      const loginPromise = componentVm(wrapper).login();
      expect(componentVm(wrapper).isLoading).toBe(true);

      await loginPromise;
      await flushPromises();

      expect(componentVm(wrapper).isLoading).toBe(false);
    });

    it('should set user to null when currentUser is not authenticated after login', async () => {
      mockAuthRepository.login.resolves();
      mockAuthRepository.currentUser.resolves({ isAuthenticated: false, username: '', token: '' });

      await wrapper.find('button').trigger('click');
      await flushPromises();

      expect(mockAuthRepository.login.called).toBe(true);
      expect(componentVm(wrapper).user).toBeNull();
      expect(wrapper.find('button').text()).toBe('Login');
    });
  });

  describe('Logout', () => {
    it('should handle successful logout', async () => {
      mockAuthRepository.logout.resolves(true);
      mockAuthRepository.authenticated.resolves(false);

      await componentVm(wrapper).logout();
      await flushPromises();

      expect(mockAuthRepository.logout.called).toBe(true);
      expect(componentVm(wrapper).isLoading).toBe(false);
      expect(componentVm(wrapper).user).toBeNull();
      expect(wrapper.find('button').text()).toBe('Login');
    });

    it('should handle failed logout attempt', async () => {
      const error = new Error('Logout failed');
      mockAuthRepository.logout.rejects(error);

      await componentVm(wrapper).logout();
      await flushPromises();

      expect(mockAuthRepository.logout.called).toBe(true);
      expect(componentVm(wrapper).isLoading).toBe(false);
      expect(consoleErrorSpy).toHaveBeenCalledWith('Logout failed:', error);
    });

    it('should set isLoading to true during logout process', async () => {
      mockAuthRepository.logout.resolves(true);
      const logoutPromise = componentVm(wrapper).logout();
      expect(componentVm(wrapper).isLoading).toBe(true);

      await logoutPromise;
      await flushPromises();

      expect(componentVm(wrapper).isLoading).toBe(false);
    });

    it('should set user to null after successful logout', async () => {
      mockAuthRepository.logout.resolves(true);
      componentVm(wrapper).user = { isAuthenticated: true, username: 'test', token: 'token' };

      await componentVm(wrapper).logout();
      await flushPromises();

      expect(componentVm(wrapper).user).toBeNull();
    });

    it('should not change user state after failed logout', async () => {
      const error = new Error('Logout failed');
      mockAuthRepository.logout.rejects(error);
      const initialUser = { isAuthenticated: true, username: 'test', token: 'token' };
      componentVm(wrapper).user = initialUser;

      await componentVm(wrapper).logout();
      await flushPromises();

      expect(componentVm(wrapper).user).toEqual(initialUser);
    });
  });
});
```

### 7. KeycloakStub.ts

Location: `src/test/webapp/unit/auth/infrastructure/secondary/KeycloakStub.ts`

This file provides a stub for Keycloak to be used in tests.

```typescript
import type Keycloak from 'keycloak-js';
import sinon from 'sinon';
import type { SinonStub } from 'sinon';

export interface KeycloakStub extends Keycloak {
  init: SinonStub;
  login: SinonStub;
  logout: SinonStub;
  register: SinonStub;
  accountManagement: SinonStub;
  updateToken: SinonStub;
  clearToken: SinonStub;
  hasRealmRole: SinonStub;
  hasResourceRole: SinonStub;
  loadUserProfile: SinonStub;
  loadUserInfo: SinonStub;
  authenticated?: boolean;
  token?: string;
  tokenParsed?: { preferred_username?: string };
}

export const stubKeycloak = (): KeycloakStub =>
  ({
    init: sinon.stub(),
    login: sinon.stub(),
    logout: sinon.stub(),
    register: sinon.stub(),
    accountManagement: sinon.stub(),
    updateToken: sinon.stub(),
    clearToken: sinon.stub(),
    hasRealmRole: sinon.stub(),
    hasResourceRole: sinon.stub(),
    loadUserProfile: sinon.stub(),
    loadUserInfo: sinon.stub(),
    authenticated: false,
    token: undefined,
    tokenParsed: undefined,
  }) as KeycloakStub;
```

### 8. AxiosAuthInterceptor.spec.ts

Location: `src/test/webapp/unit/shared/http/infrastructure/secondary/AxiosAuthInterceptor.spec.ts`

This file contains unit tests for the AxiosAuthInterceptor.

```typescript
import { describe, it, expect, beforeEach } from 'vitest';
import type { InternalAxiosRequestConfig, AxiosError, AxiosResponse } from 'axios';
import { setupAxiosInterceptors } from '@/shared/http/infrastructure/secondary/AxiosAuthInterceptor';
import { AUTH_REPOSITORY } from '@/auth/application/AuthProvider';
import { provide } from '@/injections';
import sinon from 'sinon';
import type { SinonStub } from 'sinon';
import type { AuthRepository } from '@/auth/domain/AuthRepository';
import { stubAxiosInstance, dataAxiosResponse } from './AxiosStub';
import type { AxiosStubInstance } from './AxiosStub';
import { AxiosHeaders } from 'axios';

interface MockAuthRepository extends AuthRepository {
  authenticated: SinonStub;
  refreshToken: SinonStub;
  logout: SinonStub;
}

describe('AxiosAuthInterceptor', () => {
  let axiosInstance: AxiosStubInstance;
  let mockAuthRepository: MockAuthRepository;

  beforeEach(() => {
    axiosInstance = stubAxiosInstance();
    mockAuthRepository = {
      currentUser: sinon.stub(),
      login: sinon.stub(),
      logout: sinon.stub(),
      authenticated: sinon.stub(),
      refreshToken: sinon.stub(),
    };
    provide(AUTH_REPOSITORY, mockAuthRepository);
  });

  const setupInterceptors = () => {
    setupAxiosInterceptors(axiosInstance);
  };

  it('should add Authorization header for authenticated requests', async () => {
    mockAuthRepository.authenticated.resolves(true);
    mockAuthRepository.refreshToken.resolves('fake-token');
    setupInterceptors();
    const config: InternalAxiosRequestConfig = { headers: new AxiosHeaders() };

    const interceptedConfig = await axiosInstance.runInterceptors(config);

    expect(mockAuthRepository.authenticated.called).toBe(true);
    expect(mockAuthRepository.refreshToken.called).toBe(true);
    expect(interceptedConfig.headers.get('Authorization')).toBe('Bearer fake-token');
  });

  it('should not add Authorization header for unauthenticated requests', async () => {
    mockAuthRepository.authenticated.resolves(false);
    setupInterceptors();
    const config: InternalAxiosRequestConfig = { headers: new AxiosHeaders() };

    const interceptedConfig = await axiosInstance.runInterceptors(config);

    expect(mockAuthRepository.authenticated.called).toBe(true);
    expect(mockAuthRepository.refreshToken.called).toBe(false);
    expect(interceptedConfig.headers.get('Authorization')).toBeUndefined();
  });

  it('should call logout on 401 response', async () => {
    setupInterceptors();
    const error: AxiosError = {
      response: {
        status: 401,
        data: {},
        statusText: '',
        headers: {},
        config: {} as InternalAxiosRequestConfig,
      },
      isAxiosError: true,
      toJSON: () => ({}),
      name: '',
      message: '',
    };
    const responseInterceptor = axiosInstance.interceptors.response.use.args[0][1];

    const interceptorPromise = responseInterceptor(error);

    await expect(interceptorPromise).rejects.toEqual(error);
    expect(mockAuthRepository.logout.called).toBe(true);
  });

  it('should not call logout for non-401 errors', async () => {
    setupInterceptors();
    const error: AxiosError = {
      response: {
        status: 500,
        data: {},
        statusText: 'Internal Server Error',
        headers: {},
        config: {} as InternalAxiosRequestConfig,
      },
      isAxiosError: true,
      toJSON: () => ({}),
      name: 'AxiosError',
      message: 'Request failed with status code 500',
    };

    const responseInterceptor = axiosInstance.interceptors.response.use.args[0][1];

    await expect(responseInterceptor(error)).rejects.toEqual(error);

    expect(mockAuthRepository.logout.called).toBe(false);
  });

  it('should not call logout for errors without response', async () => {
    setupInterceptors();
    const error: AxiosError = {
      isAxiosError: true,
      toJSON: () => ({}),
      name: 'AxiosError',
      message: 'Network Error',
    };

    const responseInterceptor = axiosInstance.interceptors.response.use.args[0][1];

    await expect(responseInterceptor(error)).rejects.toEqual(error);

    expect(mockAuthRepository.logout.called).toBe(false);
  });

  it('should pass through successful responses without modification', async () => {
    setupInterceptors();

    const mockResponse: AxiosResponse = {
      data: { message: 'Success' },
      status: 200,
      statusText: 'OK',
      headers: {},
      config: {} as InternalAxiosRequestConfig,
    };

    const responseInterceptor = axiosInstance.interceptors.response.use.args[0][0];

    const result = await responseInterceptor(mockResponse);

    expect(result).toEqual(mockResponse);
  });
});
```

### 9. AxiosStub.ts

Location: `src/test/webapp/unit/shared/http/infrastructure/secondary/AxiosStub.ts`

This file provides a stub for Axios to be used in tests.

```typescript
import type { AxiosInstance, AxiosInterceptorManager, AxiosResponse, InternalAxiosRequestConfig } from 'axios';
import type { SinonStub } from 'sinon';
import sinon from 'sinon';

export interface AxiosStubInterceptorManager extends AxiosInterceptorManager<InternalAxiosRequestConfig> {
  use: SinonStub;
  eject: SinonStub;
  clear: SinonStub;
}

export interface AxiosStubInstance extends AxiosInstance {
  get: SinonStub;
  put: SinonStub;
  post: SinonStub;
  delete: SinonStub;
  interceptors: {
    request: AxiosStubInterceptorManager;
    response: AxiosStubInterceptorManager;
  };
  runInterceptors: (config: InternalAxiosRequestConfig) => Promise<InternalAxiosRequestConfig>;
}

export const stubAxiosInstance = (): AxiosStubInstance => {
  const instance = {
    get: sinon.stub(),
    put: sinon.stub(),
    post: sinon.stub(),
    delete: sinon.stub(),
    interceptors: {
      request: {
        use: sinon.stub(),
        eject: sinon.stub(),
        clear: sinon.stub(),
      },
      response: {
        use: sinon.stub(),
        eject: sinon.stub(),
        clear: sinon.stub(),
      },
    },
    runInterceptors: async (config: InternalAxiosRequestConfig) => {
      let currentConfig = { ...config, headers: config.headers || {} };
      for (const interceptor of instance.interceptors.request.use.args) {
        currentConfig = await interceptor[0](currentConfig);
      }
      return currentConfig;
    },
  } as AxiosStubInstance;
  return instance;
};

export const dataAxiosResponse = <T>(data: T): AxiosResponse<T> =>
  ({
    data,
  }) as AxiosResponse<T>;
```

## Conclusion

This documentation offers a detailed overview of the authentication components and utilities within our Vue.js application. It covers both the primary application files related to routing and authentication, as well as the test files used to validate the functionality of these components.

At the core of the authentication system is the `AuthVue` component, which manages user login and logout processes. The `AxiosAuthInterceptor` ensures that all authenticated requests are properly equipped with the necessary authorization headers.

The accompanying test files (`AuthVueComponent.spec.ts`, `AxiosAuthInterceptor.spec.ts`) illustrate how to effectively test these components, while the stub files (`KeycloakStub.ts`, `AxiosStub.ts`) provide mock implementations of external dependencies, enabling more streamlined and reliable testing.
