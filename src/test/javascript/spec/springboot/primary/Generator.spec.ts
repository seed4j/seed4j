import { ProjectService } from '@/springboot/domain/ProjectService';
import { GeneratorVue } from '@/springboot/primary';
import { shallowMount, VueWrapper } from '@vue/test-utils';
import { stubProjectService } from '../domain/ProjectService.fixture';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from './ProjectToUpdate.fixture';
import { stubReactService } from '../domain/client/ReactService.fixture';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { stubSpringBootService } from '../domain/SpringBootService.fixture';
import { SpringBootService } from '@/springboot/domain/SpringBootService';
import { stubVueService } from '../domain/client/VueService.fixture';
import { VueService } from '@/springboot/domain/client/VueService';
import { stubLogger } from '../../common/domain/Logger.fixture';
import { Logger } from '@/common/domain/Logger';

let wrapper: VueWrapper;

interface WrapperOptions {
  logger: Logger;
  projectService: ProjectService;
  reactService: ReactService;
  springBootService: SpringBootService;
  vueService: VueService;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { logger, projectService, reactService, springBootService, vueService }: WrapperOptions = {
    logger: stubLogger(),
    projectService: stubProjectService(),
    reactService: stubReactService(),
    springBootService: stubSpringBootService(),
    vueService: stubVueService(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(GeneratorVue, {
    global: {
      provide: {
        logger,
        projectService,
        reactService,
        springBootService,
        vueService,
      },
    },
  });
};

const fillFullForm = async (projectToUpdate: ProjectToUpdate): Promise<void> => {
  const projectPathInput = wrapper.find('#path');
  await projectPathInput.setValue(projectToUpdate.folder);
  const baseNameInput = wrapper.find('#basename');
  await baseNameInput.setValue(projectToUpdate.baseName);
  const projectNameInput = wrapper.find('#projectname');
  await projectNameInput.setValue(projectToUpdate.projectName);
  const packageNameInput = wrapper.find('#packagename');
  await packageNameInput.setValue(projectToUpdate.packageName);
  const serverPortInput = wrapper.find('#serverport');
  await serverPortInput.setValue(projectToUpdate.serverPort);
};

const selectSection = async (value: string): Promise<void> => {
  const radio = wrapper.find('#option-' + value);
  await radio.setValue(value);
  await radio.trigger('click');

  const section = wrapper.find('#section-' + value);
  await section.trigger('click');
};

describe('Generator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should not init project when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.init.resolves({});
    await wrap({ projectService });

    const initButton = wrapper.find('#init');
    await initButton.trigger('click');

    expect(projectService.init.called).toBe(false);
  });

  it('should init project when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.init.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#init');
    await initButton.trigger('click');

    const args = projectService.init.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on init failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.init.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#init');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Project initialization failed');
  });

  it('should not add Maven when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addMaven.resolves({});
    await wrap({ projectService });

    const button = wrapper.find('#maven');
    await button.trigger('click');

    expect(projectService.addMaven.called).toBe(false);
  });

  it('should add Maven when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addMaven.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#maven');
    await button.trigger('click');

    const args = projectService.addMaven.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding maven failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.addMaven.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#maven');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Maven to project failed');
  });

  it('should not add JaCoCo when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addJaCoCo.resolves({});
    await wrap({ projectService });

    const button = wrapper.find('#jacoco');
    await button.trigger('click');

    expect(projectService.addJaCoCo.called).toBe(false);
  });

  it('should add JaCoCo when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addJaCoCo.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#jacoco');
    await button.trigger('click');

    const args = projectService.addJaCoCo.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding JaCoCo failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.addJaCoCo.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#jacoco');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding JaCoCo to project failed');
  });

  it('should not add Sonar Backend when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addSonarBackend.resolves({});
    await wrap({ projectService });

    const button = wrapper.find('#sonar-backend');
    await button.trigger('click');

    expect(projectService.addSonarBackend.called).toBe(false);
  });

  it('should add Sonar Backend when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addSonarBackend.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#sonar-backend');
    await button.trigger('click');

    const args = projectService.addSonarBackend.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding Sonar Backend failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.addSonarBackend.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#sonar-backend');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Sonar Backend to project failed');
  });

  it('should not add Sonar Backend+Frontend when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addSonarBackendFrontend.resolves({});
    await wrap({ projectService });

    const button = wrapper.find('#sonar-backend-frontend');
    await button.trigger('click');

    expect(projectService.addSonarBackendFrontend.called).toBe(false);
  });

  it('should add Sonar Backend+Frontend when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addSonarBackendFrontend.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#sonar-backend-frontend');
    await button.trigger('click');

    const args = projectService.addSonarBackendFrontend.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding Sonar Backend+Frontend failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.addSonarBackendFrontend.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#sonar-backend-frontend');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Sonar Backend+Frontend to project failed');
  });

  it('should not add JavaBase when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addJavaBase.resolves({});
    await wrap({ projectService });

    const button = wrapper.find('#javabase');
    await button.trigger('click');

    expect(projectService.addJavaBase.called).toBe(false);
  });

  it('should add JavaBase when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addJavaBase.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#javabase');
    await button.trigger('click');

    const args = projectService.addJavaBase.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding java base failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.addJavaBase.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#javabase');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Java Base to project failed');
  });

  it('should not add SpringBoot when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBoot.resolves({});
    await wrap({ springBootService });
    await selectSection('springboot');

    const button = wrapper.find('#springboot');
    await button.trigger('click');

    expect(springBootService.addSpringBoot.called).toBe(false);
  });

  it('should add SpringBoot when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBoot.resolves({});
    await wrap({ springBootService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const button = wrapper.find('#springboot');
    await button.trigger('click');

    const args = springBootService.addSpringBoot.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding spring boot failure', async () => {
    const logger = stubLogger();
    const springBootService = stubSpringBootService();
    springBootService.addSpringBoot.rejects({});
    await wrap({ springBootService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const initButton = wrapper.find('#springboot');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding SpringBoot to project failed');
  });

  it('should not add SpringBoot MVC with Tomcat when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcTomcat.resolves({});
    await wrap({ springBootService });
    await selectSection('springboot');

    const button = wrapper.find('#springbootmvctomcat');
    await button.trigger('click');

    expect(springBootService.addSpringBootMvcTomcat.called).toBe(false);
  });

  it('should add SpringBoot MVC with Tomcat when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcTomcat.resolves({});
    await wrap({ springBootService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const button = wrapper.find('#springbootmvctomcat');
    await button.trigger('click');

    const args = springBootService.addSpringBootMvcTomcat.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding SpringBoot MVC with Tomcat failure', async () => {
    const logger = stubLogger();
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootMvcTomcat.rejects({});
    await wrap({ springBootService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const initButton = wrapper.find('#springbootmvctomcat');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding SpringBoot MVC with Tomcat to project failed');
  });

  it('should not add SpringBoot Webflux with Netty when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootWebfluxNetty.resolves({});
    await wrap({ springBootService });
    await selectSection('springboot');

    const button = wrapper.find('#springbootwebfluxnetty');
    await button.trigger('click');

    expect(springBootService.addSpringBootWebfluxNetty.called).toBe(false);
  });

  it('should add SpringBoot Webflux with Netty when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootWebfluxNetty.resolves({});
    await wrap({ springBootService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const button = wrapper.find('#springbootwebfluxnetty');
    await button.trigger('click');

    const args = springBootService.addSpringBootWebfluxNetty.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding SpringBoot Webflux with Netty failure', async () => {
    const logger = stubLogger();
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootWebfluxNetty.rejects({});
    await wrap({ springBootService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const initButton = wrapper.find('#springbootwebfluxnetty');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding SpringBoot Webflux with Netty to project failed');
  });

  it('should not add SpringBoot Actuator when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootActuator.resolves({});
    await wrap({ springBootService });
    await selectSection('springboot');

    const button = wrapper.find('#springboot-actuator');
    await button.trigger('click');

    expect(springBootService.addSpringBootActuator.called).toBe(false);
  });

  it('should add SpringBoot Actuator when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootActuator.resolves({});
    await wrap({ springBootService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const button = wrapper.find('#springboot-actuator');
    await button.trigger('click');

    const args = springBootService.addSpringBootActuator.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding SpringBoot Actuator failure', async () => {
    const logger = stubLogger();
    const springBootService = stubSpringBootService();
    springBootService.addSpringBootActuator.rejects({});
    await wrap({ springBootService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const initButton = wrapper.find('#springboot-actuator');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding SpringBoot Actuator to project failed');
  });

  describe('Log tools', () => {
    describe('AOP Logging', () => {
      it('should not add SpringBoot AOP Logging when project path is not filled', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootAopLogging.resolves({});
        await wrap({ springBootService });
        await selectSection('springboot');

        const button = wrapper.find('#springboot-aop');
        await button.trigger('click');

        expect(springBootService.addSpringBootAopLogging.called).toBe(false);
      });

      it('should add SpringBoot AOP Logging when project path is filled', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootAopLogging.resolves({});
        await wrap({ springBootService });
        const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
          folder: 'project/path',
          baseName: 'beer',
          projectName: 'Beer Project',
          packageName: 'tech.jhipster.beer',
          serverPort: '8080',
        });
        await fillFullForm(projectToUpdate);
        await selectSection('springboot');

        const button = wrapper.find('#springboot-aop');
        await button.trigger('click');

        const args = springBootService.addSpringBootAopLogging.getCall(0).args[0];
        expect(args).toEqual({
          baseName: 'beer',
          folder: 'project/path',
          projectName: 'Beer Project',
          packageName: 'tech.jhipster.beer',
          serverPort: 8080,
        });
      });

      it('should handle error on adding SpringBoot AOP Logging failure', async () => {
        const logger = stubLogger();
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootAopLogging.rejects({});
        await wrap({ springBootService, logger });
        const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
        await fillFullForm(projectToUpdate);
        await selectSection('springboot');

        const initButton = wrapper.find('#springboot-aop');
        await initButton.trigger('click');

        const [message] = logger.error.getCall(0).args;
        expect(message).toBe('Adding SpringBoot AOP Logging to project failed');
      });
    });

    describe('Logstash', () => {
      it('should not add SpringBoot Logstash when project path is not filled', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootLogstash.resolves({});
        await wrap({ springBootService });
        await selectSection('springboot');

        const button = wrapper.find('#springboot-logstash');
        await button.trigger('click');

        expect(springBootService.addSpringBootLogstash.called).toBe(false);
      });

      it('should add SpringBoot Logstash when project path is filled', async () => {
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootLogstash.resolves({});
        await wrap({ springBootService });
        const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
          folder: 'project/path',
          baseName: 'beer',
          projectName: 'Beer Project',
          packageName: 'tech.jhipster.beer',
          serverPort: '8080',
        });
        await fillFullForm(projectToUpdate);
        await selectSection('springboot');

        const button = wrapper.find('#springboot-logstash');
        await button.trigger('click');

        const args = springBootService.addSpringBootLogstash.getCall(0).args[0];
        expect(args).toEqual({
          baseName: 'beer',
          folder: 'project/path',
          projectName: 'Beer Project',
          packageName: 'tech.jhipster.beer',
          serverPort: 8080,
        });
      });

      it('should handle error on adding SpringBoot Logstash failure', async () => {
        const logger = stubLogger();
        const springBootService = stubSpringBootService();
        springBootService.addSpringBootLogstash.rejects({});
        await wrap({ springBootService, logger });
        const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
        await fillFullForm(projectToUpdate);
        await selectSection('springboot');

        const initButton = wrapper.find('#springboot-logstash');
        await initButton.trigger('click');

        const [message] = logger.error.getCall(0).args;
        expect(message).toBe('Adding SpringBoot Logstash to project failed');
      });
    });
  });

  it('should not add SpringBoot Security JWT when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addJWT.resolves({});
    await wrap({ springBootService });
    await selectSection('springboot');

    const button = wrapper.find('#springboot-jwt');
    await button.trigger('click');

    expect(springBootService.addJWT.called).toBe(false);
  });

  it('should add SpringBoot Security JWT when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addJWT.resolves({});
    await wrap({ springBootService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const button = wrapper.find('#springboot-jwt');
    await button.trigger('click');

    const args = springBootService.addJWT.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding SpringBoot Security JWT failure', async () => {
    const logger = stubLogger();
    const springBootService = stubSpringBootService();
    springBootService.addJWT.rejects({});
    await wrap({ springBootService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const initButton = wrapper.find('#springboot-jwt');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding SpringBoot Security JWT to project failed');
  });

  it('should not add SpringBoot Security JWT Basic Auth  when project path is not filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addBasicAuthJWT.resolves({});
    await wrap({ springBootService });
    await selectSection('springboot');

    const button = wrapper.find('#springboot-jwt-basic-auth');
    await button.trigger('click');

    expect(springBootService.addBasicAuthJWT.called).toBe(false);
  });

  it('should add SpringBoot Security JWT Basic Auth when project path is filled', async () => {
    const springBootService = stubSpringBootService();
    springBootService.addBasicAuthJWT.resolves({});
    await wrap({ springBootService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const button = wrapper.find('#springboot-jwt-basic-auth');
    await button.trigger('click');

    const args = springBootService.addBasicAuthJWT.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding SpringBoot Security JWT Basic Auth failure', async () => {
    const logger = stubLogger();
    const springBootService = stubSpringBootService();
    springBootService.addBasicAuthJWT.rejects({});
    await wrap({ springBootService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('springboot');

    const initButton = wrapper.find('#springboot-jwt-basic-auth');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding SpringBoot Security JWT Basic Auth to project failed');
  });

  describe('Databases', () => {
    it('should not add SpringBoot Database PostgreSQL  when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addPostgres.resolves({});
      await wrap({ springBootService });
      await selectSection('springboot');

      const button = wrapper.find('#springboot-database-postgresql');
      await button.trigger('click');

      expect(springBootService.addPostgres.called).toBe(false);
    });

    it('should add SpringBoot Database PostgreSQL when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addPostgres.resolves({});
      await wrap({ springBootService });
      const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
        folder: 'project/path',
        baseName: 'beer',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: '8080',
      });
      await fillFullForm(projectToUpdate);
      await selectSection('springboot');

      const button = wrapper.find('#springboot-database-postgresql');
      await button.trigger('click');

      const args = springBootService.addPostgres.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
    });

    it('should handle error on adding SpringBoot Database PostgreSQL failure', async () => {
      const logger = stubLogger();
      const springBootService = stubSpringBootService();
      springBootService.addPostgres.rejects({});
      await wrap({ springBootService, logger });
      const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
      await fillFullForm(projectToUpdate);
      await selectSection('springboot');

      const initButton = wrapper.find('#springboot-database-postgresql');
      await initButton.trigger('click');

      const [message] = logger.error.getCall(0).args;
      expect(message).toBe('Adding SpringBoot Database PostgreSQL to project failed');
    });

    it('should not add SpringBoot Database MySQL  when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMySQL.resolves({});
      await wrap({ springBootService });
      await selectSection('springboot');

      const button = wrapper.find('#springboot-database-mysql');
      await button.trigger('click');

      expect(springBootService.addMySQL.called).toBe(false);
    });

    it('should add SpringBoot Database MySQL when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMySQL.resolves({});
      await wrap({ springBootService });
      const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
        folder: 'project/path',
        baseName: 'beer',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: '8080',
      });
      await fillFullForm(projectToUpdate);
      await selectSection('springboot');

      const button = wrapper.find('#springboot-database-mysql');
      await button.trigger('click');

      const args = springBootService.addMySQL.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
    });

    it('should handle error on adding SpringBoot Database MySQL failure', async () => {
      const logger = stubLogger();
      const springBootService = stubSpringBootService();
      springBootService.addMySQL.rejects({});
      await wrap({ springBootService, logger });
      const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
      await fillFullForm(projectToUpdate);
      await selectSection('springboot');

      const initButton = wrapper.find('#springboot-database-mysql');
      await initButton.trigger('click');

      const [message] = logger.error.getCall(0).args;
      expect(message).toBe('Adding SpringBoot Database MySQL to project failed');
    });

    it('should not add SpringBoot Database MariaDB  when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMariaDB.resolves({});
      await wrap({ springBootService });
      await selectSection('springboot');

      const button = wrapper.find('#springboot-database-mariadb');
      await button.trigger('click');

      expect(springBootService.addMariaDB.called).toBe(false);
    });

    it('should add SpringBoot Database MariaDB when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMariaDB.resolves({});
      await wrap({ springBootService });
      const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
        folder: 'project/path',
        baseName: 'beer',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: '8080',
      });
      await fillFullForm(projectToUpdate);
      await selectSection('springboot');

      const button = wrapper.find('#springboot-database-mariadb');
      await button.trigger('click');

      const args = springBootService.addMariaDB.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
    });

    it('should handle error on adding SpringBoot Database MariaDB failure', async () => {
      const logger = stubLogger();
      const springBootService = stubSpringBootService();
      springBootService.addMariaDB.rejects({});
      await wrap({ springBootService, logger });
      const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
      await fillFullForm(projectToUpdate);
      await selectSection('springboot');

      const initButton = wrapper.find('#springboot-database-mariadb');
      await initButton.trigger('click');

      const [message] = logger.error.getCall(0).args;
      expect(message).toBe('Adding SpringBoot Database MariaDB to project failed');
    });

    it('should not add SpringBoot Database MongoDB  when project path is not filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMongoDB.resolves({});
      await wrap({ springBootService });
      await selectSection('springboot');

      const button = wrapper.find('#springboot-database-mongodb');
      await button.trigger('click');

      expect(springBootService.addMongoDB.called).toBe(false);
    });

    it('should add SpringBoot Database MongoDB when project path is filled', async () => {
      const springBootService = stubSpringBootService();
      springBootService.addMongoDB.resolves({});
      await wrap({ springBootService });
      const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
        folder: 'project/path',
        baseName: 'beer',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: '8080',
      });
      await fillFullForm(projectToUpdate);
      await selectSection('springboot');

      const button = wrapper.find('#springboot-database-mongodb');
      await button.trigger('click');

      const args = springBootService.addMongoDB.getCall(0).args[0];
      expect(args).toEqual({
        baseName: 'beer',
        folder: 'project/path',
        projectName: 'Beer Project',
        packageName: 'tech.jhipster.beer',
        serverPort: 8080,
      });
    });

    it('should handle error on adding SpringBoot Database MongoDB failure', async () => {
      const logger = stubLogger();
      const springBootService = stubSpringBootService();
      springBootService.addMongoDB.rejects({});
      await wrap({ springBootService, logger });
      const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
      await fillFullForm(projectToUpdate);
      await selectSection('springboot');

      const initButton = wrapper.find('#springboot-database-mongodb');
      await initButton.trigger('click');

      const [message] = logger.error.getCall(0).args;
      expect(message).toBe('Adding SpringBoot Database MongoDB to project failed');
    });
  });

  it('should not add React when project path is not filled', async () => {
    const reactService = stubReactService();
    reactService.add.resolves({});
    await wrap({ reactService });
    await selectSection('react');

    const button = wrapper.find('#react');
    await button.trigger('click');

    expect(reactService.add.called).toBe(false);
  });

  it('should add React when project path is filled', async () => {
    const reactService = stubReactService();
    reactService.add.resolves({});
    await wrap({ reactService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('react');

    const button = wrapper.find('#react');
    await button.trigger('click');

    const args = reactService.add.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should add React with Style when checkbox is checked', async () => {
    const reactService = stubReactService();
    reactService.addWithStyle.resolves({});
    await wrap({ reactService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('react');

    const checkbox = wrapper.find('#react-with-style');
    await checkbox.setValue(true);
    const button = wrapper.find('#react');
    await button.trigger('click');

    const args = reactService.addWithStyle.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding React failure', async () => {
    const logger = stubLogger();
    const reactService = stubReactService();
    reactService.add.rejects({});
    await wrap({ reactService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('react');

    const initButton = wrapper.find('#react');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding React to project failed');
  });

  it('should handle error on adding React with style failure', async () => {
    const logger = stubLogger();
    const reactService = stubReactService();
    reactService.addWithStyle.rejects({});
    await wrap({ reactService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('react');

    const checkbox = wrapper.find('#react-with-style');
    await checkbox.setValue(true);
    const initButton = wrapper.find('#react');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding React with style to project failed');
  });

  it('should not add Vue when project path is not filled', async () => {
    const vueService = stubVueService();
    vueService.add.resolves({});
    await wrap({ vueService });
    await selectSection('vue');

    const button = wrapper.find('#vue');
    await button.trigger('click');

    expect(vueService.add.called).toBe(false);
  });

  it('should add Vue when project path is filled', async () => {
    const vueService = stubVueService();
    vueService.add.resolves({});
    await wrap({ vueService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('vue');

    const button = wrapper.find('#vue');
    await button.trigger('click');

    const args = vueService.add.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should add Vue with Style when checkbox is checked', async () => {
    const vueService = stubVueService();
    vueService.addWithStyle.resolves({});
    await wrap({ vueService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);
    await selectSection('vue');

    const checkbox = wrapper.find('#vue-with-style');
    await checkbox.setValue(true);
    const button = wrapper.find('#vue');
    await button.trigger('click');

    const args = vueService.addWithStyle.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding Vue failure', async () => {
    const logger = stubLogger();
    const vueService = stubVueService();
    vueService.add.rejects({});
    await wrap({ vueService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('vue');

    const initButton = wrapper.find('#vue');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Vue to project failed');
  });

  it('should handle error on adding Vue with style failure', async () => {
    const logger = stubLogger();
    const vueService = stubVueService();
    vueService.addWithStyle.rejects({});
    await wrap({ vueService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);
    await selectSection('vue');

    const checkbox = wrapper.find('#vue-with-style');
    await checkbox.setValue(true);
    const initButton = wrapper.find('#vue');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Vue with style to project failed');
  });

  it('should not add Frontend Maven Plugin when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addFrontendMavenPlugin.resolves({});
    await wrap({ projectService });

    const button = wrapper.find('#frontend-maven-plugin');
    await button.trigger('click');

    expect(projectService.addFrontendMavenPlugin.called).toBe(false);
  });

  it('should add Frontend Maven Plugin when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addFrontendMavenPlugin.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#frontend-maven-plugin');
    await button.trigger('click');

    const args = projectService.addFrontendMavenPlugin.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should handle error on adding Frontend Maven Plugin failure', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.addFrontendMavenPlugin.rejects({});
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const initButton = wrapper.find('#frontend-maven-plugin');
    await initButton.trigger('click');

    const [message] = logger.error.getCall(0).args;
    expect(message).toBe('Adding Frontend Maven Plugin to project failed');
  });

  it('should download initialized project with basename', async () => {
    const projectService = stubProjectService();
    projectService.download.resolves({});
    // @ts-ignore
    global.URL.createObjectURL = jest.fn(() => new Blob([{}], { type: 'application/zip' }));

    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#download');
    await button.trigger('click');

    const args = projectService.download.getCall(0).args[0];
    expect(args).toEqual({
      baseName: 'beer',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should download initialized project without basename', async () => {
    const projectService = stubProjectService();
    projectService.download.resolves({});
    // @ts-ignore
    global.URL.createObjectURL = jest.fn(() => new Blob([{}], { type: 'application/zip' }));

    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: '',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await fillFullForm(projectToUpdate);

    const button = wrapper.find('#download');
    await button.trigger('click');

    const args = projectService.download.getCall(0).args[0];
    expect(args).toEqual({
      baseName: '',
      folder: 'project/path',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: 8080,
    });
  });

  it('should not download an non existing project', async () => {
    const logger = stubLogger();
    const projectService = stubProjectService();
    projectService.download.rejects(new Error('foo'));
    await wrap({ projectService, logger });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate();
    await fillFullForm(projectToUpdate);

    const downloadButton = wrapper.find('#download');
    await downloadButton.trigger('click');

    const [message, error] = logger.error.getCall(0).args;
    expect(message).toBe('Downloading project failed');
    expect(error).toStrictEqual(new Error('foo'));
  });
});
