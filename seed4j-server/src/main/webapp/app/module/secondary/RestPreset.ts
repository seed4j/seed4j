import { Preset } from '@/module/domain/Preset';
import { RestPresetModuleToApply, mapPresetModulesToApply } from '@/module/secondary/RestPresetModuleToApply';

export interface RestPreset {
  name: string;
  modules: RestPresetModuleToApply[];
}

export const mapToPreset = (restPreset: RestPreset): Preset => ({
  name: restPreset.name,
  modules: mapPresetModulesToApply(restPreset.modules),
});
