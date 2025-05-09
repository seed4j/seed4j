import { describe, expect, it } from 'vitest';

import { ManagementInfo } from '@/module/domain/ManagementInfo';
import { RestManagementRepository } from '@/module/secondary/RestManagementRepository';

import { dataBackendResponse, stubAxiosHttp } from '../../shared/http/infrastructure/secondary/AxiosHttpStub';

describe('Rest management repository', () => {
  it('should get info using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestManagementRepository(axiosInstance);
    axiosInstance.get.resolves(dataBackendResponse(restManagementInfo()));

    const managementInfo = await repository.getInfo();

    expect(managementInfo).toEqual(info);
  });
});

const info = {
  git: {
    commit: {
      id: {
        describe: 'feat(rest): add management info',
        abbrev: 'b3f4',
      },
    },
    branch: 'main',
    build: {
      version: '1.2.3',
      time: '19:00',
    },
  },
};

const restManagementInfo = (): ManagementInfo => info;
