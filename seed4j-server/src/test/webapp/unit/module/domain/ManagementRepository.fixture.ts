import { ManagementRepository } from '@/module/domain/ManagementRepository';
import { type MockedFunction, vi } from 'vitest';

export interface ManagementRepositoryStub extends ManagementRepository {
  getInfo: MockedFunction<any>;
}

export const stubLocalManagementRepository = (): ManagementRepositoryStub => ({ getInfo: vi.fn() }) as ManagementRepositoryStub;
