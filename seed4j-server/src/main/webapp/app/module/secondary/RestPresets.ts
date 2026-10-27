import { Presets } from '@/module/domain/Presets';
import { RestPreset, mapToPreset } from '@/module/secondary/RestPreset';
import { AxiosResponse } from 'axios';

export interface RestPresets {
  presets: RestPreset[];
}

export const mapToPresets = (response: AxiosResponse<RestPresets>): Presets => {
  const data = response.data;
  return {
    presets: data.presets.map(mapToPreset),
  };
};
