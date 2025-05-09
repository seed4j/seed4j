import { AxiosResponse } from 'axios';

import { Category } from '../domain/Category';
import { Modules } from '../domain/Modules';
import { RestCategory } from './RestCategory';
import { toModule } from './RestModule';

export interface RestModules {
  categories: RestCategory[];
}

export const mapToModules = (response: AxiosResponse<RestModules>): Modules => new Modules(response.data.categories.map(toCategory));

const toCategory = (restCategory: RestCategory): Category => ({
  name: restCategory.name,
  modules: restCategory.modules.map(toModule),
});
