import { AUTH_REPOSITORY, provideForAuth } from '@/auth/application/AuthProvider';
import { JwtAuthRepository } from '@/auth/infrastructure/secondary/JwtAuthRepository';
import { inject } from '@/injections';
import { describe, expect, it } from 'vitest';
import { stubAxiosHttp } from '../../shared/http/infrastructure/secondary/AxiosHttpStub';

describe('AuthProvider', () => {
  it('should define AUTH_REPOSITORY with the correct key', () => {
    expect(AUTH_REPOSITORY.description).toBe('AuthRepository');
  });

  it('should provide JwtAuthRepository', () => {
    const mockAxiosHttp = stubAxiosHttp();
    provideForAuth(mockAxiosHttp);

    const injectedRepository = inject(AUTH_REPOSITORY);

    expect(injectedRepository).toBeInstanceOf(JwtAuthRepository);
  });
});
