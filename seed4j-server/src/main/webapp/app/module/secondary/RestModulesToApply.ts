import { ModulesToApply } from '@/module/domain/ModulesToApply';
import { RestModuleToApply } from '@/module/secondary/RestModuleToApply';

export interface RestModulesToApply {
  modules: string[];
  properties: RestModuleToApply;
}

export const toRestModulesToApply = (modulesToApply: ModulesToApply): RestModulesToApply => ({
  modules: modulesToApply.modules.map(module => module.get()),
  properties: {
    projectFolder: modulesToApply.projectFolder,
    commit: modulesToApply.commit,
    parameters: Object.fromEntries(modulesToApply.parameters),
  },
});
