import { ModuleParametersRepository } from '../domain/ModuleParametersRepository';
import { ModuleParameterType } from '../domain/ModuleParameters';

const STORAGE_KEY_MODULE_PARAMETERS_SUFIX = '_moduleParameters';
const STORAGE_KEY_CURRENT_FOLDER_PATH = 'currentFolderPath';

export class LocalStorageModuleParametersRepository implements ModuleParametersRepository {
  private readonly localStorage: Storage;

  constructor(localStorage: Storage) {
    this.localStorage = localStorage;
    this.localStorage.removeItem(STORAGE_KEY_CURRENT_FOLDER_PATH);
  }

  store(folderPath: string, map: Map<string, ModuleParameterType>): void {
    this.localStorage.setItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFIX, JSON.stringify(Array.from(map.entries())));
  }

  get(folderPath: string): Map<string, ModuleParameterType> {
    this.localStorage.setItem(STORAGE_KEY_CURRENT_FOLDER_PATH, folderPath);
    const storedValue = this.localStorage.getItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFIX);
    if (storedValue) {
      return new Map(JSON.parse(storedValue));
    }
    return new Map<string, ModuleParameterType>();
  }

  getCurrentFolderPath(): string {
    const storedValue = this.localStorage.getItem(STORAGE_KEY_CURRENT_FOLDER_PATH);
    if (storedValue) {
      return storedValue;
    }
    return '';
  }
}
