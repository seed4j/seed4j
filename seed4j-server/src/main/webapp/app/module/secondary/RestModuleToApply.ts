import { ModuleToApply } from '@/module/domain/ModuleToApply';

export interface RestModuleToApply {
  projectFolder: string;
  commit: boolean;
  parameters: {};
}

export const toRestModuleToApply = (moduleToApply: ModuleToApply): RestModuleToApply => ({
  projectFolder: moduleToApply.projectFolder,
  commit: moduleToApply.commit,
  parameters: Object.fromEntries(moduleToApply.parameters),
});
