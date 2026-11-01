import { Landscape } from '@/module/domain/landscape/Landscape';
import { Modules } from '@/module/domain/Modules';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { ModulesToApply } from '@/module/domain/ModulesToApply';
import { ModuleToApply } from '@/module/domain/ModuleToApply';
import { Presets } from '@/module/domain/Presets';
import { Project } from '@/module/domain/Project';
import { ProjectFolder } from '@/module/domain/ProjectFolder';
import { ProjectHistory } from '@/module/domain/ProjectHistory';
import { RestLandscape, mapToLandscape } from '@/module/secondary/RestLandscape';
import { RestModules, mapToModules } from '@/module/secondary/RestModules';
import { RestModulesToApply, toRestModulesToApply } from '@/module/secondary/RestModulesToApply';
import { RestModuleToApply, toRestModuleToApply } from '@/module/secondary/RestModuleToApply';
import { RestPresets, mapToPresets } from '@/module/secondary/RestPresets';
import { RestProjectHistory, mapToModuleHistory } from '@/module/secondary/RestProjectHistory';
import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import { Optional } from '@/shared/optional/domain/Optional';
import { AxiosResponse, RawAxiosRequestConfig } from 'axios';

export class RestModulesRepository implements ModulesRepository {
  constructor(private readonly axiosInstance: AxiosHttp) {}

  list(): Promise<Modules> {
    return this.axiosInstance.get<RestModules>('/api/modules').then(mapToModules);
  }

  landscape(): Promise<Landscape> {
    return this.axiosInstance.get<RestLandscape>('/api/modules-landscape').then(mapToLandscape);
  }

  async apply(module: ModuleSlug, moduleToApply: ModuleToApply): Promise<void> {
    await this.axiosInstance.post<void, RestModuleToApply>(`/api/modules/${module.get()}/apply-patch`, toRestModuleToApply(moduleToApply));
  }

  async applyAll(modulesToApply: ModulesToApply): Promise<void> {
    await this.axiosInstance.post<void, RestModulesToApply>('/api/apply-patches', toRestModulesToApply(modulesToApply));
  }

  history(folder: ProjectFolder): Promise<ProjectHistory> {
    return this.axiosInstance.get<RestProjectHistory>(`/api/projects?path=${encodeURI(folder)}`).then(mapToModuleHistory);
  }

  download(folder: ProjectFolder): Promise<Project> {
    const config: RawAxiosRequestConfig = {
      responseType: 'blob',
      headers: {
        Accept: 'application/octet-stream',
      },
    };

    return this.axiosInstance.get<ArrayBuffer>(`/api/projects?path=${encodeURI(folder)}`, config).then(mapToProject);
  }

  preset(): Promise<Presets> {
    return this.axiosInstance.get<RestPresets>('/api/presets').then(mapToPresets);
  }
}

const mapToProject = (response: AxiosResponse<ArrayBuffer>): Project => ({
  filename: Optional.ofNullable(response.headers['x-suggested-filename']).orElseThrow(
    () => new Error('Impossible to download file without filename'),
  ),
  content: response.data,
});
