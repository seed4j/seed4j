import { AxiosResponse } from 'axios';
import { describe, expect, it } from 'vitest';

import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';

import { dataAxiosResponse, stubAxiosInstance } from './AxiosStub';

interface Payload {
  payload: string;
}

const fakePayload = (): Payload => ({
  payload: 'content',
});

interface Result {
  result: string;
}

const fakeResult = (): Result => ({
  result: 'content',
});

const responseResult = (): AxiosResponse => dataAxiosResponse(fakeResult());

const expectForQuerying = (uri: string, result: AxiosResponse<Result>) => {
  expect(result.data).toEqual(fakeResult());
  expect(uri).toBe('/uri');
};

describe('axiosHttp', () => {
  describe('GET', () => {
    it('should get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.get.resolves(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.get<Result>('/uri');

      const [uri] = axiosInstance.get.getCall(0).args;
      expect(result.data).toEqual(fakeResult());
      expect(uri).toBe('/uri');
    });

    it('should get content with params', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.get.resolves(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      await axiosHttp.get<Result>('/uri', { params: { beer: 'chips' } });

      const [, config] = axiosInstance.get.getCall(0).args;
      expect(config.params.beer).toBe('chips');
    });
  });

  describe('PUT', () => {
    it('should only get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.put.resolves(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.put<Result>('/uri');

      const [uri] = axiosInstance.put.getCall(0).args;
      expect(result.data).toEqual(fakeResult());
      expect(uri).toBe('/uri');
    });

    it('should send and get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.put.resolves(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.put<Result, Payload>('/uri', fakePayload());

      const [uri, payload] = axiosInstance.put.getCall(0).args;
      expect(payload).toEqual<Payload>(fakePayload());
      expectForQuerying(uri, result);
    });
  });

  describe('POST', () => {
    it('should only get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.post.resolves(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.post<Result>('/uri');

      const [uri] = axiosInstance.post.getCall(0).args;
      expect(result.data).toEqual(fakeResult());
      expect(uri).toBe('/uri');
    });

    it('should send and get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.post.resolves(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.post<Result, Payload>('/uri', fakePayload());

      const [uri, payload] = axiosInstance.post.getCall(0).args;
      expect(payload).toEqual<Payload>(fakePayload());
      expectForQuerying(uri, result);
    });
  });

  describe('DELETE', () => {
    it('should get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.delete.resolves(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.delete<Result>('/uri');

      const [uri] = axiosInstance.delete.getCall(0).args;
      expect(result.data).toEqual(fakeResult());
      expect(uri).toBe('/uri');
    });
  });
});
