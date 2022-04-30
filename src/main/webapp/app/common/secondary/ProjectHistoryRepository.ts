import { Folder } from '@/springboot/domain/Folder';
import { RestHistory, toHistory } from '@/common/secondary/RestHistory';
import { History } from '@/common/domain/History';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';
import { AxiosHttp } from '@/http/AxiosHttp';

export default class ProjectHistoryRepository implements ProjectHistoryService {
  constructor(private axiosHttp: AxiosHttp, private historyStore: any) {}

  async get(folder: Folder): Promise<History> {
    return this.axiosHttp
      .get<RestHistory>('api/project-histories', { params: { folder } })
      .then(response => this.historyStore.setHistory(toHistory(response.data)));
  }
}
