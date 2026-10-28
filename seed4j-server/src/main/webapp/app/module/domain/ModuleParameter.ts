import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { ModulePropertyKey } from '@/module/domain/ModulePropertyKey';

export interface ModuleParameter {
  key: ModulePropertyKey;
  value: ModuleParameterType;
}
