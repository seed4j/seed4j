import { ManagementInfo } from '@/module/domain/ManagementInfo';

export interface ManagementRepository {
  getInfo(): Promise<ManagementInfo>;
}
