import { stubAxiosHttp } from '../../http/AxiosHttpStub';
import { describe, it, expect } from 'vitest';
import { FakeStorage } from './FakeStorage';
import { RestStoreProjectFolderRepository } from '@/module/secondary/RestStoreProjectFolderRepository';

const REST_PATH = '/tmp/jhlite/1234';
const STORED_PATH = '/path/to/project';

const makeRepository = (storage: Storage = new FakeStorage()): RestStoreProjectFolderRepository => {
  const axiosHttp = stubAxiosHttp();
  axiosHttp.get.resolves({ data: REST_PATH });

  return new RestStoreProjectFolderRepository(storage, axiosHttp);
};

describe('Rest store project folders repository', () => {
  it('Should get from storage', async () => {
    const fakeStorage = new FakeStorage();
    fakeStorage.setItem('project.folder', STORED_PATH);

    const projectFolder = await makeRepository(fakeStorage).get();

    expect(projectFolder).toEqual(STORED_PATH);
  });

  it('Should get from http', async () => {
    const projectFolder = await makeRepository().get();

    expect(projectFolder).toEqual(REST_PATH);
  });
});
