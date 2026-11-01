import { ThemeRepository } from '@/module/domain/ThemeRepository';
import { type MockedFunction, vi } from 'vitest';

export interface LocalWindowThemeRepositoryStub extends ThemeRepository {
  get: MockedFunction<any>;
  choose: MockedFunction<any>;
}

export const stubLocalWindowThemeRepository = (): LocalWindowThemeRepositoryStub => ({ get: vi.fn(), choose: vi.fn() });
