import { ModuleParameters } from '@/module/domain/ModuleParameters';
import { ProjectFolder } from '@/module/domain/ProjectFolder';

export interface ModuleToApply {
  projectFolder: ProjectFolder;
  commit: boolean;
  parameters: ModuleParameters;
}
