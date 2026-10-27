import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { ModulePropertyValue, ProjectHistory } from '@/module/domain/ProjectHistory';
import { RestAppliedModule, mapAppliedModules } from '@/module/secondary/RestAppliedModule';
import { AxiosResponse } from 'axios';

export interface RestProjectHistory {
  modules?: RestAppliedModule[];
  properties?: {};
}

export const mapToModuleHistory = (response: AxiosResponse<RestProjectHistory>): ProjectHistory => {
  const data = response.data;
  return {
    modules: mapAppliedModules(data.modules),
    properties: mapAppliedProperties(data.properties),
  };
};

const mapAppliedProperties = (properties: {} | undefined): ModulePropertyValue[] => {
  if (properties === undefined) {
    return [];
  }

  return Object.entries(properties).map(entry => ({
    key: entry[0],
    value: entry[1] as ModuleParameterType,
  }));
};
