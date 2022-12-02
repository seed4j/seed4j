import { AxiosHttp } from '@/http/AxiosHttp';
import { ProjectFolder } from '../domain/ProjectFolder';

export class RestProjectFolders {
  constructor(private axiosInstance: AxiosHttp) {}

  get(): Promise<ProjectFolder> {
    return this.axiosInstance.get<ProjectFolder>('/api/project-folders').then(res => res.data);
  }
}
