import { Landscape } from '@/module/domain/landscape/Landscape';
import { RestLandscapeLevel, toLandscapeLevel } from '@/module/secondary/RestLandscapeLevel';
import { AxiosResponse } from 'axios';

export interface RestLandscape {
  levels: RestLandscapeLevel[];
}

export const mapToLandscape = (response: AxiosResponse<RestLandscape>): Landscape =>
  Landscape.initialState(response.data.levels.map(toLandscapeLevel));
