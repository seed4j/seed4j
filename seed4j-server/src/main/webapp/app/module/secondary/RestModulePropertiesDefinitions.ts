import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { RestModulePropertyDefinition, toPropertyDefinition } from '@/module/secondary/RestModulePropertyDefinition';

export interface RestModulePropertiesDefinitions {
  definitions: RestModulePropertyDefinition[];
}

export const toPropertiesDefinitions = (propertiesDefinitions: RestModulePropertiesDefinitions | undefined): ModulePropertyDefinition[] => {
  if (!propertiesDefinitions) {
    return [];
  }

  return propertiesDefinitions.definitions.map(toPropertyDefinition);
};
