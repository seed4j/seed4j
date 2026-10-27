import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { ModulePropertyDefinitionType } from '@/module/domain/ModulePropertyDefinitionType';

export const notEmpty = (value: ModuleParameterType | undefined): boolean => !empty(value);

export const empty = (value: ModuleParameterType | undefined): boolean => {
  if (value === undefined) {
    return true;
  }

  if (typeof value === 'string') {
    return value.trim().length === 0;
  }

  return false;
};

export const castValue = (type: ModulePropertyDefinitionType, value: string): boolean | number | string => {
  switch (type) {
    case 'INTEGER':
      return Number.parseInt(value);
    case 'BOOLEAN':
      return Boolean(value);
    case 'STRING':
      return value;
  }
};
