import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';

import { ProjectFolder } from '../domain/ProjectFolder';

export class RestProjectFoldersRepository implements ProjectFoldersRepository {
  constructor(private readonly axiosInstance: AxiosHttp) {}

  get(): Promise<ProjectFolder> {
    return this.axiosInstance.get<ProjectFolder>('/api/project-folders').then(res => res.data);
  }
}
