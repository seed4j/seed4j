import sinon, { SinonStub } from 'sinon';

import { ManagementRepository } from '@/module/domain/ManagementRepository';

export interface ManagementRepositoryStub extends ManagementRepository {
  getInfo: SinonStub;
}

export const stubLocalManagementRepository = (): ManagementRepositoryStub => ({ getInfo: sinon.stub() }) as ManagementRepositoryStub;
