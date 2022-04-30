import { stubAxiosHttp } from '../../http/AxiosHttpStub';
import { createRestHistory } from './RestHistory.fixture';
import ProjectHistoryRepository from '@/common/secondary/ProjectHistoryRepository';
import { History } from '@/common/domain/History';
import { Service } from '@/common/domain/Service';
import { stubHistoryStore } from '../primary/HistoryStore.fixture';

describe('ProjectRepository', () => {
  it('should get project history', async () => {
    const historyStoreStub = stubHistoryStore();
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.get.resolves({ data: createRestHistory() });
    const projectHistoryRepository = new ProjectHistoryRepository(axiosHttpStub, historyStoreStub);

    await projectHistoryRepository.get('folder/path');

    const [uri, config] = axiosHttpStub.get.getCall(0).args;
    expect(uri).toBe('api/project-histories');
    expect(config).toEqual({ params: { folder: 'folder/path' } });
    const [historyStored] = historyStoreStub.setHistory.getCall(0).args;
    expect(historyStored).toEqual<History>({
      services: [Service.INITIALIZATION, Service.JAVA_BASE, Service.MAVEN_JAVA],
    });
  });
});
