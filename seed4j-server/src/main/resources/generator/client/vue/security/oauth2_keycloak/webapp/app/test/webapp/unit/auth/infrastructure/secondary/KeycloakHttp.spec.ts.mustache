import { KeycloakHttp } from '@/auth/infrastructure/secondary/KeycloakHttp';
import { describe, expect, it } from 'vitest';
import { fakeAuthenticatedUser } from './KeycloakHttpStub';
import { stubKeycloak } from './KeycloakStub';

const createKeycloakHttp = () => {
  const keycloakStub = stubKeycloak();
  const keycloakHttp = new KeycloakHttp(keycloakStub);
  return { keycloakStub, keycloakHttp };
};

describe('KeycloakHttp', () => {
  describe('Authentication', () => {
    it('should authenticate successfully', async () => {
      const { keycloakStub, keycloakHttp } = createKeycloakHttp();
      const fakeUser = fakeAuthenticatedUser();
      keycloakStub.init.mockResolvedValue(true);
      keycloakStub.authenticated = true;
      keycloakStub.tokenParsed = { preferred_username: fakeUser.username };
      keycloakStub.token = fakeUser.token;

      const result = await keycloakHttp.currentUser();

      expect(result).toEqual(fakeUser);
      expect(keycloakStub.init).toHaveBeenCalledOnce();
    });

    it('should handle authentication failure', async () => {
      const { keycloakStub, keycloakHttp } = createKeycloakHttp();
      keycloakStub.init.mockResolvedValue(false);

      const result = await keycloakHttp.currentUser();

      expect(result).toEqual({ isAuthenticated: false, username: '', token: '' });
      expect(keycloakStub.init).toHaveBeenCalledOnce();
    });
  });

  describe('Initialization', () => {
    it('should not reinitialize if already initialized', async () => {
      const { keycloakStub, keycloakHttp } = createKeycloakHttp();
      keycloakStub.init.mockResolvedValue(true);

      await keycloakHttp.currentUser();
      await keycloakHttp.currentUser();

      expect(keycloakStub.init).toHaveBeenCalledOnce();
    });

    it('should initialize only once across different method calls', async () => {
      const { keycloakStub, keycloakHttp } = createKeycloakHttp();
      keycloakStub.init.mockResolvedValue(true);

      await keycloakHttp.currentUser();
      await keycloakHttp.login();
      await keycloakHttp.logout();
      await keycloakHttp.authenticated();
      await keycloakHttp.refreshToken();

      expect(keycloakStub.init).toHaveBeenCalledOnce();
    });
  });

  it('should handle undefined preferred_username', async () => {
    const { keycloakStub, keycloakHttp } = createKeycloakHttp();
    keycloakStub.init.mockResolvedValue(true);
    keycloakStub.authenticated = true;
    keycloakStub.tokenParsed = {};
    keycloakStub.token = 'test-token';

    const result = await keycloakHttp.currentUser();

    expect(result).toEqual({
      isAuthenticated: true,
      username: '',
      token: 'test-token',
    });
  });

  it('should handle undefined token', async () => {
    const { keycloakStub, keycloakHttp } = createKeycloakHttp();
    keycloakStub.init.mockResolvedValue(true);
    keycloakStub.authenticated = true;
    keycloakStub.tokenParsed = { preferred_username: 'test' };
    keycloakStub.token = undefined;

    const result = await keycloakHttp.currentUser();

    expect(result).toEqual({
      isAuthenticated: true,
      username: 'test',
      token: '',
    });
  });

  it('should logout', async () => {
    const { keycloakStub, keycloakHttp } = createKeycloakHttp();
    keycloakStub.logout.mockResolvedValue(undefined);

    await keycloakHttp.logout();

    expect(keycloakStub.logout).toHaveBeenCalledOnce();
  });

  it('should check if authenticated', async () => {
    const { keycloakStub, keycloakHttp } = createKeycloakHttp();
    keycloakStub.init.mockResolvedValue(true);
    keycloakStub.authenticated = true;
    keycloakStub.token = 'valid-token';

    const result = await keycloakHttp.authenticated();

    expect(result).toBe(true);
    expect(keycloakStub.init).toHaveBeenCalledOnce();
  });

  it('should refresh token', async () => {
    const { keycloakStub, keycloakHttp } = createKeycloakHttp();
    const newToken = 'new-test-token';
    keycloakStub.updateToken.mockResolvedValue(undefined);
    keycloakStub.token = newToken;

    const result = await keycloakHttp.refreshToken();

    expect(result).toBe(newToken);
    expect(keycloakStub.updateToken).toHaveBeenCalledOnce();
  });
});

it('should login successfully', async () => {
  const { keycloakStub, keycloakHttp } = createKeycloakHttp();
  keycloakStub.init.mockResolvedValue(true);
  keycloakStub.login.mockResolvedValue(undefined);

  await keycloakHttp.login();

  expect(keycloakStub.init).toHaveBeenCalledOnce();
  expect(keycloakStub.login).toHaveBeenCalledOnce();
});
