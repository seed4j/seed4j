import { stubAxiosHttp } from '../../../http/AxiosHttpStub';
import { Project } from '@/springboot/domain/Project';
import { createProject } from '../../domain/Project.fixture';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import VueRepository from '@/springboot/secondary/client/VueRepository';
import { stubProjectHistoryService } from '../../../common/domain/ProjectHistoryService.fixture';

const PROJECT_FOLDER = 'folder/path';

describe('VueRepository', () => {
  it('should add Vue', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const vueRepository = new VueRepository(axiosHttpStub, projectHistoryService);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await vueRepository.add(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/clients/vue');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add Vue with minimal style', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const vueRepository = new VueRepository(axiosHttpStub, projectHistoryService);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await vueRepository.addWithStyle(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/clients/vue/styles');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });
});
