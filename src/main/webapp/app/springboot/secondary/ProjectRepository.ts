import { Project } from '@/springboot/domain/Project';
import { ProjectService } from '@/springboot/domain/ProjectService';
import { AxiosHttp } from '@/http/AxiosHttp';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import { DocumentFile } from '@/common/domain/DocumentFile';
import { toDocumentFile } from '@/common/secondary/ResponseDocumentFile';
import { AxiosRequestConfig } from 'axios';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';

export default class ProjectRepository implements ProjectService {
  constructor(private axiosHttp: AxiosHttp, private projectHistoryService: ProjectHistoryService) {}

  private async postAndGetHistory(url: string, restProject: RestProject): Promise<void> {
    this.axiosHttp.post(url, restProject).then(() => this.projectHistoryService.get(restProject.folder));
  }

  async init(project: Project): Promise<void> {
    await this.postAndGetHistory('api/inits/full', toRestProject(project));
  }

  async addMaven(project: Project): Promise<void> {
    await this.postAndGetHistory('api/build-tools/maven', toRestProject(project));
  }

  async addJaCoCo(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/java/jacoco-minimum-coverage', toRestProject(project));
  }

  async addSonarBackend(project: Project): Promise<void> {
    await this.postAndGetHistory('api/developer-tools/sonar/java-backend', toRestProject(project));
  }

  async addSonarBackendFrontend(project: Project): Promise<void> {
    await this.postAndGetHistory('api/developer-tools/sonar/java-backend-and-frontend', toRestProject(project));
  }

  async addFrontendMavenPlugin(project: Project): Promise<void> {
    await this.postAndGetHistory('api/developer-tools/frontend-maven-plugin', toRestProject(project));
  }

  async addJavaBase(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/java/base', toRestProject(project));
  }

  async download(project: Project): Promise<DocumentFile> {
    const restProject: RestProject = toRestProject(project);
    const requestConfig: AxiosRequestConfig = {
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
      },
    };
    return this.axiosHttp.post<ArrayBuffer, RestProject>('api/projects/download', restProject, requestConfig).then(async response => {
      await this.projectHistoryService.get(project.folder);
      return toDocumentFile('export.xls')(response);
    });
  }
}
