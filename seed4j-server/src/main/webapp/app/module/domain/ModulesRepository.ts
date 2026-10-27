import { Landscape } from '@/module/domain/landscape/Landscape';
import { Modules } from '@/module/domain/Modules';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ModulesToApply } from '@/module/domain/ModulesToApply';
import { ModuleToApply } from '@/module/domain/ModuleToApply';
import { Presets } from '@/module/domain/Presets';
import { Project } from '@/module/domain/Project';
import { ProjectFolder } from '@/module/domain/ProjectFolder';
import { ProjectHistory } from '@/module/domain/ProjectHistory';

export interface ModulesRepository {
  list(): Promise<Modules>;

  landscape(): Promise<Landscape>;

  apply(module: ModuleSlug, moduleToApply: ModuleToApply): Promise<void>;

  applyAll(modulesToApply: ModulesToApply): Promise<void>;

  history(folder: ProjectFolder): Promise<ProjectHistory>;

  download(folder: ProjectFolder): Promise<Project>;

  preset(): Promise<Presets>;
}
