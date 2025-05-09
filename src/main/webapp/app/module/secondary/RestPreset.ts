import { Preset } from '@/module/domain/Preset';

import { mapPresetModulesToApply, RestPresetModuleToApply } from './RestPresetModuleToApply';

export interface RestPreset {
  name: string;
  modules: RestPresetModuleToApply[];
}

export const mapToPreset = (restPreset: RestPreset): Preset => {
  return {
    name: restPreset.name,
    modules: mapPresetModulesToApply(restPreset.modules),
  };
};
