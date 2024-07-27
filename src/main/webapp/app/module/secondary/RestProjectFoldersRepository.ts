import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import { ProjectFolder } from '../domain/ProjectFolder';
import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';

export class RestProjectFoldersRepository implements ProjectFoldersRepository {
  constructor(private axiosInstance: AxiosHttp) {}

  get(): Promise<ProjectFolder> {
    return this.axiosInstance.get<ProjectFolder>('/api/project-folders').then(res => res.data);
  }
}
