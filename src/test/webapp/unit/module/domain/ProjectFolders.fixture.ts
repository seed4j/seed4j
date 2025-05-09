import sinon, { SinonStub } from 'sinon';

import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';

export interface ProjectFoldersRepositoryStub extends ProjectFoldersRepository {
  get: SinonStub;
}

export const stubProjectFoldersRepository = (): ProjectFoldersRepositoryStub =>
  ({
    get: sinon.stub(),
  }) as ProjectFoldersRepositoryStub;
