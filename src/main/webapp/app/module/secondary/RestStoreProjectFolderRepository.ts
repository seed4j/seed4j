import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { ProjectFolder } from '@/module/domain/ProjectFolder';
import { RestProjectFolders } from '@/module/secondary/RestProjectFolders';
import { AxiosHttp } from '@/http/AxiosHttp';
import { StorageProjectFolder } from '@/module/secondary/StorageProjectFolder';

export class RestStoreProjectFolderRepository implements ProjectFoldersRepository {
  private rest: RestProjectFolders;
  private storage: StorageProjectFolder;

  constructor(storage: Storage, axiosHttp: AxiosHttp) {
    this.storage = new StorageProjectFolder(storage);
    this.rest = new RestProjectFolders(axiosHttp);
  }

  get(): Promise<ProjectFolder> {
    return this.storage
      .get()
      .map(value => Promise.resolve(value))
      .orElseGet(() => this.rest.get());
  }
}
