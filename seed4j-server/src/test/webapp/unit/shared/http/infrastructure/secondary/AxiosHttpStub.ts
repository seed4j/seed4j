import { AxiosHttp, AxiosHttpResponse } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import { type MockedFunction, vi } from 'vitest';

export interface AxiosHttpStub extends AxiosHttp {
  get: MockedFunction<any>;
  post: MockedFunction<any>;
  delete: MockedFunction<any>;
  put: MockedFunction<any>;
}

export const stubAxiosHttp = (): AxiosHttpStub =>
  ({
    get: vi.fn(),
    post: vi.fn(),
    delete: vi.fn(),
    put: vi.fn(),
  }) as AxiosHttpStub;

export const dataBackendResponse = <T>(data: T): AxiosHttpResponse<T> =>
  ({
    data,
  }) as AxiosHttpResponse<T>;
