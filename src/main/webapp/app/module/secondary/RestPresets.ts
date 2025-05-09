import { AxiosResponse } from 'axios';

import { Presets } from '@/module/domain/Presets';

import { mapToPreset, RestPreset } from './RestPreset';

export interface RestPresets {
  presets: RestPreset[];
}

export const mapToPresets = (response: AxiosResponse<RestPresets>): Presets => {
  const data = response.data;
  return {
    presets: data.presets.map(mapToPreset),
  };
};
