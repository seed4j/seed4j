import { describe, expect, it } from 'vitest';

import { RestProjectFoldersRepository } from '@/module/secondary/RestProjectFoldersRepository';

import { stubAxiosHttp } from '../../shared/http/infrastructure/secondary/AxiosHttpStub';

describe('Rest project folders repository', () => {
  it('should get project folder using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestProjectFoldersRepository(axiosInstance);
    axiosInstance.get.resolves({ data: '/tmp/jhlite/1234' });

    const projectFolder = await repository.get();

    expect(projectFolder).toBe('/tmp/jhlite/1234');
  });
});
