import { AUTH_REPOSITORY, provideForAuth } from '@/auth/application/AuthProvider';
import { KeycloakAuthRepository } from '@/auth/infrastructure/secondary/KeycloakAuthRepository';
import { inject } from '@/injections';
import { describe, expect, it } from 'vitest';
import { stubKeycloakHttp } from '../infrastructure/secondary/KeycloakHttpStub';

describe('AuthProvider', () => {
  it('should define AUTH_REPOSITORY with the correct key', () => {
    expect(AUTH_REPOSITORY.description).toBe('AuthRepository');
  });

  it('should provide KeycloakAuthRepository with KeycloakHttp', async () => {
    const keycloakHttp = stubKeycloakHttp();
    provideForAuth(keycloakHttp);

    const injectedRepository = inject(AUTH_REPOSITORY);
    expect(injectedRepository).toBeInstanceOf(KeycloakAuthRepository);
  });
});
