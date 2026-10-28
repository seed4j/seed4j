import { ModuleSlug } from '@/module/domain/ModuleSlug';

type PresetName = string;

export interface Preset {
  name: PresetName;
  modules: ModuleSlug[];
}
