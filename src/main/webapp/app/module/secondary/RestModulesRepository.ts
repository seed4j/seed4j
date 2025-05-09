import { AxiosResponse, RawAxiosRequestConfig } from 'axios';

import { Presets } from '@/module/domain/Presets';
import { mapToPresets, RestPresets } from '@/module/secondary/RestPresets';
import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import { Optional } from '@/shared/optional/domain/Optional';

import { Landscape } from '../domain/landscape/Landscape';
import { Modules } from '../domain/Modules';
import { ModuleSlug } from '../domain/ModuleSlug';
import { ModulesRepository } from '../domain/ModulesRepository';
import { ModulesToApply } from '../domain/ModulesToApply';
import { ModuleToApply } from '../domain/ModuleToApply';
import { Project } from '../domain/Project';
import { ProjectFolder } from '../domain/ProjectFolder';
import { ProjectHistory } from '../domain/ProjectHistory';
import { mapToLandscape, RestLandscape } from './RestLandscape';
import { mapToModules, RestModules } from './RestModules';
import { RestModulesToApply, toRestModulesToApply } from './RestModulesToApply';
import { RestModuleToApply, toRestModuleToApply } from './RestModuleToApply';
import { mapToModuleHistory, RestProjectHistory } from './RestProjectHistory';

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

  async format(folder: ProjectFolder): Promise<void> {
    await this.axiosInstance.post<void, RestModuleToApply>(`/api/format-project?path=${encodeURI(folder)}`);
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
