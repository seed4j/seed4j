import { Presets } from '@/module/domain/Presets';

import { Landscape } from './landscape/Landscape';
import { Modules } from './Modules';
import { ModuleSlug } from './ModuleSlug';
import { ModulesToApply } from './ModulesToApply';
import { ModuleToApply } from './ModuleToApply';
import { Project } from './Project';
import { ProjectFolder } from './ProjectFolder';
import { ProjectHistory } from './ProjectHistory';

export interface ModulesRepository {
  list(): Promise<Modules>;

  landscape(): Promise<Landscape>;

  apply(module: ModuleSlug, moduleToApply: ModuleToApply): Promise<void>;

  applyAll(modulesToApply: ModulesToApply): Promise<void>;

  history(folder: ProjectFolder): Promise<ProjectHistory>;

  format(folder: ProjectFolder): Promise<void>;

  download(folder: ProjectFolder): Promise<Project>;

  preset(): Promise<Presets>;
}
