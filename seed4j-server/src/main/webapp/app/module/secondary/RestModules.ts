import { Category } from '@/module/domain/Category';
import { Modules } from '@/module/domain/Modules';
import { RestCategory } from '@/module/secondary/RestCategory';
import { toModule } from '@/module/secondary/RestModule';
import { AxiosResponse } from 'axios';

export interface RestModules {
  categories: RestCategory[];
}

export const mapToModules = (response: AxiosResponse<RestModules>): Modules => new Modules(response.data.categories.map(toCategory));

const toCategory = (restCategory: RestCategory): Category => ({
  name: restCategory.name,
  modules: restCategory.modules.map(toModule),
});
