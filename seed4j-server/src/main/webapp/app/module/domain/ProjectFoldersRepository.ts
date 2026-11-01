import { ProjectFolder } from '@/module/domain/ProjectFolder';

export interface ProjectFoldersRepository {
  get(): Promise<ProjectFolder>;
}
