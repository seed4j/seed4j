import { AxiosResponse } from 'axios';

import { ModuleParameterType } from '../domain/ModuleParameters';
import { ModulePropertyValue, ProjectHistory } from '../domain/ProjectHistory';
import { mapAppliedModules, RestAppliedModule } from './RestAppliedModule';

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

  return Object.entries(properties).map(entry => {
    return {
      key: entry[0],
      value: entry[1] as ModuleParameterType,
    };
  });
};
