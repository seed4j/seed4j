import { Project } from '@/springboot/domain/Project';
import { SpringBootService } from '@/springboot/domain/SpringBootService';
import { AxiosHttp } from '@/http/AxiosHttp';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';

export default class SpringBootRepository implements SpringBootService {
  constructor(private axiosHttp: AxiosHttp, private projectHistoryService: ProjectHistoryService) {}

  private async postAndGetHistory(url: string, restProject: RestProject): Promise<void> {
    this.axiosHttp.post(url, restProject).then(() => this.projectHistoryService.get(restProject.folder));
  }

  async addSpringBoot(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot', toRestProject(project));
  }

  async addSpringBootMvcTomcat(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/web-servers/tomcat', toRestProject(project));
  }

  async addSpringBootWebfluxNetty(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/reactive-servers/netty', toRestProject(project));
  }

  async addSpringBootActuator(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/technical-tools/actuator', toRestProject(project));
  }

  async addSpringBootAopLogging(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/log-tools/aop', toRestProject(project));
  }

  async addSpringBootLogstash(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/log-tools/logstash', toRestProject(project));
  }

  async addSpringBootBannerIppon(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/banners/ippon', toRestProject(project));
  }

  async addSpringBootBannerJHipsterV2(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/banners/jhipster-v2', toRestProject(project));
  }

  async addSpringBootBannerJHipsterV3(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/banners/jhipster-v3', toRestProject(project));
  }

  async addSpringBootBannerJHipsterV7(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/banners/jhipster-v7', toRestProject(project));
  }

  async addSpringBootBannerJHipsterV7React(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/banners/jhipster-v7-react', toRestProject(project));
  }

  async addSpringBootBannerJHipsterV7Vue(project: Project): Promise<void> {
    await this.postAndGetHistory('api/servers/spring-boot/banners/jhipster-v7-vue', toRestProject(project));
  }

  async addPostgres(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/databases/postgresql', toRestProject(project));
  }

  async addMySQL(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/databases/mysql', toRestProject(project));
  }

  async addMariaDB(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/databases/mariadb', toRestProject(project));
  }

  async addMongoDB(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/databases/mongodb', toRestProject(project));
  }
  async addSpringBootFlywayInit(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/database-migration-tools/flyway', toRestProject(project));
  }

  async addSpringBootFlywayUser(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/database-migration-tools/flyway/user', toRestProject(project));
  }

  async addSpringBootLiquibaseInit(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/database-migration-tools/liquibase', toRestProject(project));
  }

  async addSpringBootLiquibaseUser(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/database-migration-tools/liquibase/user', toRestProject(project));
  }

  async addJWT(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/security-systems/jwt', toRestProject(project));
  }

  async addBasicAuthJWT(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/security-systems/jwt/basic-auth', toRestProject(project));
  }

  async addOauth2(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/security-systems/oauth2', toRestProject(project));
  }

  async addSpringdocJWT(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/api-documentations/springdoc/init-with-security-jwt', toRestProject(project));
  }

  async addSpringBootAsync(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/async', toRestProject(project));
  }

  async addSpringBootDevtoolsDependencies(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/technical-tools/devtools', toRestProject(project));
  }

  async addSpringBootDockerfile(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/containers/docker/dockerfile', toRestProject(project));
  }

  async addSpringBootDockerJib(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/containers/docker/jib', toRestProject(project));
  }

  async addSpringCloudConfigClient(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/distributed-systems/spring-cloud/config-client', toRestProject(project));
  }

  async addSpringCloudConsul(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/distributed-systems/spring-cloud/consul', toRestProject(project));
  }

  async addSpringCloudEureka(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/distributed-systems/spring-cloud/eureka-client', toRestProject(project));
  }

  async addEhcacheWithJavaConf(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/caches/ehcache/java-configuration', toRestProject(project));
  }

  async addEhcacheWithXML(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/caches/ehcache/xml-configuration', toRestProject(project));
  }

  async addSimpleCache(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/servers/spring-boot/caches/simple', toRestProject(project));
  }
}
