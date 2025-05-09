import sinon, { SinonStub } from 'sinon';

import { ModuleParametersRepository } from '@/module/domain/ModuleParametersRepository';

export interface ModuleParametersRepositoryStub extends ModuleParametersRepository {
  store: SinonStub;
  storeCurrentFolderPath: SinonStub;
  get: SinonStub;
  getCurrentFolderPath: SinonStub;
}

export const stubModuleParametersRepository = (): ModuleParametersRepositoryStub =>
  ({
    store: sinon.stub(),
    storeCurrentFolderPath: sinon.stub(),
    get: sinon.stub(),
    getCurrentFolderPath: sinon.stub(),
  }) as ModuleParametersRepositoryStub;
