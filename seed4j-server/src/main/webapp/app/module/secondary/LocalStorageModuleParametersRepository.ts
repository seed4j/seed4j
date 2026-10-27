import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { ModuleParametersRepository } from '@/module/domain/ModuleParametersRepository';

const STORAGE_KEY_MODULE_PARAMETERS_SUFFIX = '_moduleParameters';
const STORAGE_KEY_CURRENT_FOLDER_PATH = 'currentFolderPath';

export class LocalStorageModuleParametersRepository implements ModuleParametersRepository {
  private readonly localStorage: Storage;

  constructor(localStorage: Storage) {
    this.localStorage = localStorage;
    this.localStorage.removeItem(STORAGE_KEY_CURRENT_FOLDER_PATH);
  }

  store(folderPath: string, map: Map<string, ModuleParameterType>): void {
    this.localStorage.setItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFFIX, JSON.stringify(Array.from(map.entries())));
  }

  get(folderPath: string): Map<string, ModuleParameterType> {
    this.localStorage.setItem(STORAGE_KEY_CURRENT_FOLDER_PATH, folderPath);
    const storedValue = this.localStorage.getItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFFIX);
    if (storedValue) {
      return new Map(JSON.parse(storedValue));
    }
    return new Map<string, ModuleParameterType>();
  }

  getCurrentFolderPath(): string {
    return this.localStorage.getItem(STORAGE_KEY_CURRENT_FOLDER_PATH) ?? '';
  }
}
