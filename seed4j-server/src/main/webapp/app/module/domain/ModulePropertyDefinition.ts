import { ModulePropertyDefaultValue } from '@/module/domain/ModulePropertyDefaultValue';
import { ModulePropertyDefinitionType } from '@/module/domain/ModulePropertyDefinitionType';
import { ModulePropertyDescription } from '@/module/domain/ModulePropertyDescription';
import { ModulePropertyKey } from '@/module/domain/ModulePropertyKey';

export interface ModulePropertyDefinition {
  type: ModulePropertyDefinitionType;
  mandatory: boolean;
  key: ModulePropertyKey;
  description?: ModulePropertyDescription;
  defaultValue?: ModulePropertyDefaultValue;
  order: number;
}
