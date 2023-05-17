import { ModuleParameterType } from '@/module/domain/ModuleParameters';

export interface ModuleParametersRepository {
  store(folderPath: string, map: Map<string, ModuleParameterType>): void;
  get(folderPath: string): Map<string, ModuleParameterType>;
  getCurrentFolderPath(): string;
}
