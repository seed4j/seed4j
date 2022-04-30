import { stubAxiosHttp } from '../../../http/AxiosHttpStub';
import { Project } from '@/springboot/domain/Project';
import { createProject } from '../../domain/Project.fixture';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import AngularRepository from '@/springboot/secondary/client/AngularRepository';
import { stubProjectHistoryService } from '../../../common/domain/ProjectHistoryService.fixture';

const PROJECT_FOLDER = 'folder/path';

describe('AngularRepository', () => {
  it('should add Angular', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const angularRepository = new AngularRepository(axiosHttpStub, projectHistoryService);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await angularRepository.add(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/clients/angular');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });
});
