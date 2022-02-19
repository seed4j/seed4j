import { AxiosInstance, AxiosResponse } from 'axios';
import sinon, { SinonStub } from 'sinon';

export interface AxiosStubInstance extends AxiosInstance {
  get: SinonStub;
  put: SinonStub;
  post: SinonStub;
  delete: SinonStub;
}

export const stubAxiosInstance = (): AxiosStubInstance =>
  ({
    get: sinon.stub(),
    put: sinon.stub(),
    post: sinon.stub(),
    delete: sinon.stub(),
  } as AxiosStubInstance);

export const dataAxiosResponse = <T>(data: T): AxiosResponse<T> =>
  ({
    data,
  } as AxiosResponse<T>);
