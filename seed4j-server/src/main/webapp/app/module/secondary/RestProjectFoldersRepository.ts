import { ProjectFolder } from '@/module/domain/ProjectFolder';
import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';

export class RestProjectFoldersRepository implements ProjectFoldersRepository {
  constructor(private readonly axiosInstance: AxiosHttp) {}

  get(): Promise<ProjectFolder> {
    return this.axiosInstance.get<ProjectFolder>('/api/project-folders').then(res => res.data);
  }
}
