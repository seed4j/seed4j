import sinon, { SinonStub } from 'sinon';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';

export interface ProjectHistoryServiceFixture extends ProjectHistoryService {
  get: SinonStub;
}

export const stubProjectHistoryService = (): ProjectHistoryServiceFixture => ({
  get: sinon.stub(),
});
