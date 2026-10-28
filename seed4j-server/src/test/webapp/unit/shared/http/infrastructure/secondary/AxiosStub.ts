import { AxiosInstance, AxiosResponse } from 'axios';
import { type MockedFunction, vi } from 'vitest';

export interface AxiosStubInstance extends AxiosInstance {
  get: MockedFunction<any>;
  put: MockedFunction<any>;
  post: MockedFunction<any>;
  delete: MockedFunction<any>;
}

export const stubAxiosInstance = (): AxiosStubInstance =>
  ({
    get: vi.fn(),
    put: vi.fn(),
    post: vi.fn(),
    delete: vi.fn(),
  }) as AxiosStubInstance;

export const dataAxiosResponse = <T>(data: T): AxiosResponse<T> =>
  ({
    data,
  }) as AxiosResponse<T>;
