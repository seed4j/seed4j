import { ModuleSlug } from '@/module/domain/ModuleSlug';

export interface ProjectHistory {
  modules: ModuleSlug[];
  properties: ModulePropertyValue[];
}

export interface ModulePropertyValue {
  key: string;
  value: string | boolean | number;
}
