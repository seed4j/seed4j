import { LocalStorageModuleParametersRepository } from '@/module/secondary/LocalStorageModuleParametersRepository';
import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { describe, it, expect } from 'vitest';

const fakeStorage = (): Storage => {
  let store: { [key: string]: string } = {};
  return {
    getItem: (key: string) => store[key],
    setItem: (key: string, value: string) => (store[key] = value),
    removeItem: (key: string) => delete store[key],
    clear: () => (store = {}),
    get length() {
      return Object.keys(store).length;
    },
    key: (index: number) => Object.keys(store)[index],
  };
};

const STORAGE_KEY_MODULE_PARAMETERS_SUFIX = '_moduleParameters';
const STORAGE_KEY_CURRENT_FOLDER_PATH = 'currentFolderPath';

describe('LocalStorageModuleParametersRepository', () => {
  it('Should store the module parameters using folder path as the key in localStorage', () => {
    const folderPath = '/tmp/jhlite/1234';
    const storage = fakeStorage();
    storage.clear();
    const data: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    const repo = new LocalStorageModuleParametersRepository(storage);

    repo.store(folderPath, new Map(data));

    expect(JSON.parse(storage.getItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFIX)!)).toEqual(data);
  });

  it('Should return empty Map when there is not module parameters in localStorage', () => {
    const folderPath = '/tmp/jhlite/1234';
    const storage = fakeStorage();
    storage.clear();

    const repo = new LocalStorageModuleParametersRepository(storage);

    expect(repo.get(folderPath)).toEqual(new Map());
  });

  it('Should return module parameters by folder path key from localStorage when it exists', () => {
    const folderPath = '/tmp/jhlite/1234';
    const data: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    const storage = fakeStorage();
    storage.clear();
    const repo = new LocalStorageModuleParametersRepository(storage);

    storage.setItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFIX, JSON.stringify(Array.from(data)));

    expect(repo.get(folderPath)).toEqual(new Map(data));
  });

  it('Should keep the previously module parameters in localStorage when store a new module parameters', () => {
    const folderPathOne = '/tmp/jhlite/one-1234';
    const folderPathTwo = '/tmp/jhlite/two-5678';
    const dataOne: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    const dataTwo: [string, ModuleParameterType][] = [
      ['key3', 'value3'],
      ['key4', 'value4'],
    ];
    const storage = fakeStorage();
    storage.clear();
    storage.setItem(folderPathOne + STORAGE_KEY_MODULE_PARAMETERS_SUFIX, JSON.stringify(Array.from(dataOne)));
    const repo = new LocalStorageModuleParametersRepository(storage);

    repo.store(folderPathTwo, new Map(dataTwo));

    expect(JSON.parse(storage.getItem(folderPathOne + STORAGE_KEY_MODULE_PARAMETERS_SUFIX)!)).toEqual(dataOne);
  });

  it('Should remove corrent folder path from localStorage when the constructor is called', () => {
    const storage = fakeStorage();
    storage.clear();
    const data: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    storage.setItem(STORAGE_KEY_CURRENT_FOLDER_PATH, JSON.stringify(Array.from(data)));

    const repo = new LocalStorageModuleParametersRepository(storage);

    expect(repo.getCurrentFolderPath()).toBe('');
  });

  it('Should store last folder path key used to get the module parameters as the current folder path in localStorage', () => {
    const storage = fakeStorage();
    storage.clear();
    const repo = new LocalStorageModuleParametersRepository(storage);
    const folderPath = '/tmp/jhlite/1234';
    const data: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    storage.setItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFIX, JSON.stringify(Array.from(data)));

    repo.get(folderPath);

    expect(storage.getItem(STORAGE_KEY_CURRENT_FOLDER_PATH)).toEqual(folderPath);
  });

  it('Should return the current folder path from localStorage when it exists', () => {
    const storage = fakeStorage();
    storage.clear();
    const repo = new LocalStorageModuleParametersRepository(storage);
    const folderPath = '/tmp/jhlite/1234';

    storage.setItem(STORAGE_KEY_CURRENT_FOLDER_PATH, folderPath);

    expect(repo.getCurrentFolderPath()).toBe(folderPath);
  });

  it('Should return empty string when there is not folder path in localStorage', () => {
    const storage = fakeStorage();
    storage.clear();

    const repo = new LocalStorageModuleParametersRepository(storage);

    expect(repo.getCurrentFolderPath()).toBe('');
  });
});
