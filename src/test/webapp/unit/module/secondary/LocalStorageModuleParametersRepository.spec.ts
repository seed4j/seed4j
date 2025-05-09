import { describe, expect, it } from 'vitest';

import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { LocalStorageModuleParametersRepository } from '@/module/secondary/LocalStorageModuleParametersRepository';

import { fakeStorage } from './FakeStorage.fixture';

const STORAGE_KEY_MODULE_PARAMETERS_SUFFIX = '_moduleParameters';
const STORAGE_KEY_CURRENT_FOLDER_PATH = 'currentFolderPath';

describe('LocalStorageModuleParametersRepository', () => {
  it('should store the module parameters using folder path as the key in localStorage', () => {
    const folderPath = '/tmp/jhlite/1234';
    const storage = fakeStorage();
    storage.clear();
    const data: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    const repo = new LocalStorageModuleParametersRepository(storage);

    repo.store(folderPath, new Map(data));

    expect(JSON.parse(storage.getItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFFIX)!)).toEqual(data);
  });

  it('should return empty Map when there is not module parameters in localStorage', () => {
    const folderPath = '/tmp/jhlite/1234';
    const storage = fakeStorage();
    storage.clear();

    const repo = new LocalStorageModuleParametersRepository(storage);

    expect(repo.get(folderPath)).toEqual(new Map());
  });

  it('should return module parameters by folder path key from localStorage when it exists', () => {
    const folderPath = '/tmp/jhlite/1234';
    const data: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    const storage = fakeStorage();
    storage.clear();
    const repo = new LocalStorageModuleParametersRepository(storage);

    storage.setItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFFIX, JSON.stringify(Array.from(data)));

    expect(repo.get(folderPath)).toEqual(new Map(data));
  });

  it('should keep the previously module parameters in localStorage when store a new module parameters', () => {
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
    storage.setItem(folderPathOne + STORAGE_KEY_MODULE_PARAMETERS_SUFFIX, JSON.stringify(Array.from(dataOne)));
    const repo = new LocalStorageModuleParametersRepository(storage);

    repo.store(folderPathTwo, new Map(dataTwo));

    expect(JSON.parse(storage.getItem(folderPathOne + STORAGE_KEY_MODULE_PARAMETERS_SUFFIX)!)).toEqual(dataOne);
  });

  it('should remove current folder path from localStorage when the constructor is called', () => {
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

  it('should store last folder path key used to get the module parameters as the current folder path in localStorage', () => {
    const storage = fakeStorage();
    storage.clear();
    const repo = new LocalStorageModuleParametersRepository(storage);
    const folderPath = '/tmp/jhlite/1234';
    const data: [string, ModuleParameterType][] = [
      ['key1', 'value1'],
      ['key2', 'value2'],
    ];
    storage.setItem(folderPath + STORAGE_KEY_MODULE_PARAMETERS_SUFFIX, JSON.stringify(Array.from(data)));

    repo.get(folderPath);

    expect(storage.getItem(STORAGE_KEY_CURRENT_FOLDER_PATH)).toEqual(folderPath);
  });

  it('should return the current folder path from localStorage when it exists', () => {
    const storage = fakeStorage();
    storage.clear();
    const repo = new LocalStorageModuleParametersRepository(storage);
    const folderPath = '/tmp/jhlite/1234';

    storage.setItem(STORAGE_KEY_CURRENT_FOLDER_PATH, folderPath);

    expect(repo.getCurrentFolderPath()).toBe(folderPath);
    expect(storage.length).toBe(1);
    expect(storage.key(0)).toBe(STORAGE_KEY_CURRENT_FOLDER_PATH);
  });

  it('should return empty string when there is not folder path in localStorage', () => {
    const storage = fakeStorage();
    storage.clear();

    const repo = new LocalStorageModuleParametersRepository(storage);

    expect(repo.getCurrentFolderPath()).toBe('');
    expect(storage.length).toBe(0);
  });
});
