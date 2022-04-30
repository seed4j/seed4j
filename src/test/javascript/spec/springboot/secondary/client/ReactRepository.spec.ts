import { stubAxiosHttp } from '../../../http/AxiosHttpStub';
import { Project } from '@/springboot/domain/Project';
import { createProject } from '../../domain/Project.fixture';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import ReactRepository from '@/springboot/secondary/client/ReactRepository';
import { stubProjectHistoryService } from '../../../common/domain/ProjectHistoryService.fixture';

const PROJECT_FOLDER = 'folder/path';

describe('ReactRepository', () => {
  it('should add React', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const reactRepository = new ReactRepository(axiosHttpStub, projectHistoryService);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await reactRepository.add(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/clients/react');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add React with minimal style', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const reactRepository = new ReactRepository(axiosHttpStub, projectHistoryService);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await reactRepository.addWithStyle(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/clients/react/styles');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });
});
