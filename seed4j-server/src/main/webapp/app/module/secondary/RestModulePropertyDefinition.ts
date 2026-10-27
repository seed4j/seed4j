import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { ModulePropertyDefinitionType } from '@/module/domain/ModulePropertyDefinitionType';

export interface RestModulePropertyDefinition {
  type: ModulePropertyDefinitionType;
  mandatory: boolean;
  key: string;
  description?: string;
  defaultValue?: string;
  order: number;
}

export const toPropertyDefinition = (propertyDefinition: RestModulePropertyDefinition): ModulePropertyDefinition => ({
  type: propertyDefinition.type,
  mandatory: propertyDefinition.mandatory,
  key: propertyDefinition.key,
  description: propertyDefinition.description,
  defaultValue: propertyDefinition.defaultValue,
  order: propertyDefinition.order,
});
