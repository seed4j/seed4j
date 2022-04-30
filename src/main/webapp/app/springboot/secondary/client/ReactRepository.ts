import { Project } from '@/springboot/domain/Project';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import { AxiosHttp } from '@/http/AxiosHttp';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';

export default class ReactRepository implements ReactService {
  constructor(private axiosHttp: AxiosHttp, private projectHistoryService: ProjectHistoryService) {}

  private async postAndGetHistory(url: string, restProject: RestProject): Promise<void> {
    this.axiosHttp.post(url, restProject).then(() => this.projectHistoryService.get(restProject.folder));
  }

  async add(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/clients/react', toRestProject(project));
  }

  async addWithStyle(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/clients/react/styles', toRestProject(project));
  }
}
