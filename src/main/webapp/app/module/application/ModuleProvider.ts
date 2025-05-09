import { key } from 'piqure';

import { provide } from '@/injections';
import { ManagementRepository } from '@/module/domain/ManagementRepository';
import { ModuleParametersRepository } from '@/module/domain/ModuleParametersRepository';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { ThemeRepository } from '@/module/domain/ThemeRepository';
import { LandscapeScroller } from '@/module/primary/landscape/LandscapeScroller';
import { LocalStorageModuleParametersRepository } from '@/module/secondary/LocalStorageModuleParametersRepository';
import { LocalWindowThemeRepository } from '@/module/secondary/LocalWindowThemeRepository';
import { RestManagementRepository } from '@/module/secondary/RestManagementRepository';
import { RestModulesRepository } from '@/module/secondary/RestModulesRepository';
import { RestProjectFoldersRepository } from '@/module/secondary/RestProjectFoldersRepository';
import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';

export const PROJECT_FOLDERS_REPOSITORY = key<ProjectFoldersRepository>('ProjectFoldersRepository');
export const MANAGEMENT_REPOSITORY = key<ManagementRepository>('ManagementRepository');
export const MODULES_REPOSITORY = key<ModulesRepository>('ModulesRepository');
export const THEMES_REPOSITORY = key<ThemeRepository>('ThemesRepository');
export const MODULE_PARAMETERS_REPOSITORY = key<ModuleParametersRepository>('ParamsRepository');
export const LANDSCAPE_SCROLLER = key<LandscapeScroller>('LandscapeScroller');

export const provideForModule = (rest: AxiosHttp): void => {
  provide(PROJECT_FOLDERS_REPOSITORY, new RestProjectFoldersRepository(rest));
  provide(MANAGEMENT_REPOSITORY, new RestManagementRepository(rest));
  provide(MODULES_REPOSITORY, new RestModulesRepository(rest));
  provide(THEMES_REPOSITORY, new LocalWindowThemeRepository(window, localStorage));
  provide(MODULE_PARAMETERS_REPOSITORY, new LocalStorageModuleParametersRepository(localStorage));
  provide(LANDSCAPE_SCROLLER, new LandscapeScroller());
};
