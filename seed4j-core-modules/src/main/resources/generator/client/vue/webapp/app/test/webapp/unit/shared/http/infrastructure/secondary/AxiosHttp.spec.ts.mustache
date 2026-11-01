import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import type { AxiosResponse } from 'axios';
import { describe, expect, it } from 'vitest';
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
      axiosInstance.get.mockResolvedValue(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.get<Result>('/uri');

      expect(axiosInstance.get).toHaveBeenCalledWith('/uri', {});
      expect(result.data).toEqual(fakeResult());
    });

    it('should get content with params', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.get.mockResolvedValue(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      await axiosHttp.get<Result>('/uri', { params: { beer: 'chips' } });

      expect(axiosInstance.get).toHaveBeenCalledWith('/uri', { params: { beer: 'chips' } });
    });
  });

  describe('PUT', () => {
    it('should only get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.put.mockResolvedValue(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.put<Result>('/uri');

      expect(axiosInstance.put).toHaveBeenCalledWith('/uri', undefined);
      expect(result.data).toEqual(fakeResult());
    });

    it('should send and get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.put.mockResolvedValue(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.put<Result, Payload>('/uri', fakePayload());

      expect(axiosInstance.put).toHaveBeenCalledWith('/uri', fakePayload());
      expectForQuerying('/uri', result);
    });
  });

  describe('POST', () => {
    it('should only get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.post.mockResolvedValue(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.post<Result>('/uri');

      expect(axiosInstance.post).toHaveBeenCalledWith('/uri', undefined, undefined);
      expect(result.data).toEqual(fakeResult());
    });

    it('should send and get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.post.mockResolvedValue(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.post<Result, Payload>('/uri', fakePayload());

      expect(axiosInstance.post).toHaveBeenCalledWith('/uri', fakePayload(), undefined);
      expectForQuerying('/uri', result);
    });
  });

  describe('DELETE', () => {
    it('should get content', async () => {
      const axiosInstance = stubAxiosInstance();
      axiosInstance.delete.mockResolvedValue(responseResult());
      const axiosHttp = new AxiosHttp(axiosInstance);

      const result = await axiosHttp.delete<Result>('/uri');

      expect(axiosInstance.delete).toHaveBeenCalledWith('/uri');
      expect(result.data).toEqual(fakeResult());
    });
  });
});
