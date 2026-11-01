import { ModuleParameters } from '@/module/domain/ModuleParameters';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ProjectFolder } from '@/module/domain/ProjectFolder';

export interface ModulesToApply {
  modules: ModuleSlug[];
  projectFolder: ProjectFolder;
  commit: boolean;
  parameters: ModuleParameters;
}
