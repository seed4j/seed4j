import { ModuleParametersRepository } from '@/module/domain/ModuleParametersRepository';
import { type MockedFunction, vi } from 'vitest';

export interface ModuleParametersRepositoryStub extends ModuleParametersRepository {
  store: MockedFunction<any>;
  storeCurrentFolderPath: MockedFunction<any>;
  get: MockedFunction<any>;
  getCurrentFolderPath: MockedFunction<any>;
}

export const stubModuleParametersRepository = (): ModuleParametersRepositoryStub =>
  ({
    store: vi.fn(),
    storeCurrentFolderPath: vi.fn(),
    get: vi.fn(),
    getCurrentFolderPath: vi.fn(),
  }) as ModuleParametersRepositoryStub;
