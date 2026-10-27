import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { type MockedFunction, vi } from 'vitest';

export interface ProjectFoldersRepositoryStub extends ProjectFoldersRepository {
  get: MockedFunction<any>;
}

export const stubProjectFoldersRepository = (): ProjectFoldersRepositoryStub =>
  ({
    get: vi.fn(),
  }) as ProjectFoldersRepositoryStub;
