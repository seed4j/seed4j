import { KeycloakAuthRepository } from '@/auth/infrastructure/secondary/KeycloakAuthRepository';
import { beforeEach, describe, expect, it } from 'vitest';
import type { KeycloakHttpStub } from './KeycloakHttpStub';
import { fakeAuthenticatedUser, stubKeycloakHttp } from './KeycloakHttpStub';

describe('KeycloakAuthRepository', () => {
  let keycloakHttpStub: KeycloakHttpStub;
  let authRepository: KeycloakAuthRepository;

  beforeEach(() => {
    keycloakHttpStub = stubKeycloakHttp();
    authRepository = new KeycloakAuthRepository(keycloakHttpStub);
  });

  it('should authenticate a user', async () => {
    const mockUser = fakeAuthenticatedUser();
    keycloakHttpStub.currentUser.mockResolvedValue(mockUser);

    const user = await authRepository.currentUser();

    expect(user).toEqual(mockUser);
    expect(keycloakHttpStub.currentUser).toHaveBeenCalledOnce();
  });

  it('should propagate error when currentUser fails', async () => {
    const error = new Error('Authentication failed');
    keycloakHttpStub.currentUser.mockRejectedValue(error);

    await expect(authRepository.currentUser()).rejects.toThrow('Authentication failed');
    expect(keycloakHttpStub.currentUser).toHaveBeenCalledOnce();
  });

  it('should log in a user successfully', async () => {
    keycloakHttpStub.login.mockResolvedValue(undefined);

    await authRepository.login();

    expect(keycloakHttpStub.login).toHaveBeenCalledOnce();
  });

  it('should propagate error when login fails', async () => {
    const error = new Error('Login failed');
    keycloakHttpStub.login.mockRejectedValue(error);

    await expect(authRepository.login()).rejects.toThrow('Login failed');
    expect(keycloakHttpStub.login).toHaveBeenCalledOnce();
  });

  it('should logout a user', async () => {
    keycloakHttpStub.logout.mockResolvedValue(undefined);

    await authRepository.logout();

    expect(keycloakHttpStub.logout).toHaveBeenCalledOnce();
  });

  it('should propagate error when logout fails', async () => {
    const error = new Error('Logout failed');
    keycloakHttpStub.logout.mockRejectedValue(error);

    await expect(authRepository.logout()).rejects.toThrow('Logout failed');
    expect(keycloakHttpStub.logout).toHaveBeenCalledOnce();
  });

  it('should check if a user is authenticated', async () => {
    keycloakHttpStub.authenticated.mockResolvedValue(true);

    const isAuthenticated = await authRepository.authenticated();

    expect(isAuthenticated).toBe(true);
    expect(keycloakHttpStub.authenticated).toHaveBeenCalledOnce();
  });

  it('should propagate error when authenticated check fails', async () => {
    const error = new Error('Authentication check failed');
    keycloakHttpStub.authenticated.mockRejectedValue(error);

    await expect(authRepository.authenticated()).rejects.toThrow('Authentication check failed');
    expect(keycloakHttpStub.authenticated).toHaveBeenCalledOnce();
  });

  it('should refresh the token', async () => {
    const newToken = 'new-test-token';
    keycloakHttpStub.refreshToken.mockResolvedValue(newToken);

    const refreshedToken = await authRepository.refreshToken();

    expect(refreshedToken).toBe(newToken);
    expect(keycloakHttpStub.refreshToken).toHaveBeenCalledOnce();
  });

  it('should propagate error when refreshToken fails', async () => {
    const error = new Error('Token refresh failed');
    keycloakHttpStub.refreshToken.mockRejectedValue(error);

    await expect(authRepository.refreshToken()).rejects.toThrow('Token refresh failed');
    expect(keycloakHttpStub.refreshToken).toHaveBeenCalledOnce();
  });
});
