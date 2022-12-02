import { ProjectFolder } from '@/module/domain/ProjectFolder';
import { Optional } from '@/common/domain/Optional';

export class StorageProjectFolder {
  constructor(private readonly storage: Storage) {}

  get(): Optional<ProjectFolder> {
    return Optional.ofNullable(this.storage.getItem('project.folder'));
  }
}
