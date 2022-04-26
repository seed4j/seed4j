import { ProjectService } from '@/springboot/domain/ProjectService';
import { defineComponent, inject, ref } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { VueService } from '@/springboot/domain/client/VueService';
import { SpringBootService } from '@/springboot/domain/SpringBootService';
import { Logger } from '@/common/domain/Logger';
import { AngularGeneratorVue } from '@/springboot/primary/angular-generator';

export default defineComponent({
  name: 'GeneratorComponent',
  components: {
    AngularGeneratorVue,
  },
  setup() {
    const logger = inject('logger') as Logger;
    const projectService = inject('projectService') as ProjectService;
    const reactService = inject('reactService') as ReactService;
    const springBootService = inject('springBootService') as SpringBootService;
    const vueService = inject('vueService') as VueService;

    const selectorPrefix = 'generator';

    const project = ref<ProjectToUpdate>({
      folder: '',
    });
    const isReactWithStyle = ref<boolean>(false);
    const isVueWithStyle = ref<boolean>(false);
    const isSvelteWithStyle = ref<boolean>(false);
    const language = ref<string>();
    const buildTool = ref<string>('maven');
    const server = ref<string>();
    const client = ref<string>();

    const initProject = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService.init(toProject(project.value)).catch(error => logger.error('Project initialization failed', error.message));
      }
    };

    const addMaven = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService.addMaven(toProject(project.value)).catch(error => logger.error('Adding Maven to project failed', error));
      }
    };

    const addJaCoCo = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService.addJaCoCo(toProject(project.value)).catch(error => logger.error('Adding JaCoCo to project failed', error));
      }
    };

    const addSonarBackend = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService
          .addSonarBackend(toProject(project.value))
          .catch(error => logger.error('Adding Sonar Backend to project failed', error));
      }
    };

    const addSonarBackendFrontend = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService
          .addSonarBackendFrontend(toProject(project.value))
          .catch(error => logger.error('Adding Sonar Backend+Frontend to project failed', error));
      }
    };

    const addJavaBase = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService
          .addJavaBase(toProject(project.value))
          .catch(error => logger.error('Adding Java Base to project failed', error));
      }
    };

    const addSpringBoot = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addSpringBoot(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot to project failed', error));
      }
    };

    const addSpringBootMvcTomcat = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addSpringBootMvcTomcat(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot MVC with Tomcat to project failed', error));
      }
    };

    const addSpringBootWebfluxNetty = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addSpringBootWebfluxNetty(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot Webflux with Netty to project failed', error));
      }
    };

    const addSpringBootActuator = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addSpringBootActuator(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot Actuator to project failed', error));
      }
    };

    const addSpringBootAopLogging = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addSpringBootAopLogging(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot AOP Logging to project failed', error));
      }
    };

    const addSpringBootLogstash = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addSpringBootLogstash(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot Logstash to project failed', error));
      }
    };

    const addSpringBootSecurityJWT = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addJWT(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot Security JWT to project failed', error));
      }
    };

    const addSpringBootSecurityJWTBasicAuth = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addBasicAuthJWT(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot Security JWT Basic Auth to project failed', error));
      }
    };

    const addPostgreSQL = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addPostgres(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot Database PostgreSQL to project failed', error));
      }
    };

    const addMySQL = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addMySQL(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot Database MySQL to project failed', error));
      }
    };

    const addMariaDB = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addMariaDB(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot Database MariaDB to project failed', error));
      }
    };

    const addMongoDB = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await springBootService
          .addMongoDB(toProject(project.value))
          .catch(error => logger.error('Adding SpringBoot Database MongoDB to project failed', error));
      }
    };

    const addReact = async (): Promise<void> => {
      if (project.value.folder !== '') {
        if (isReactWithStyle.value) {
          await reactService
            .addWithStyle(toProject(project.value))
            .catch(error => logger.error('Adding React with style to project failed', error));
        } else {
          await reactService.add(toProject(project.value)).catch(error => logger.error('Adding React to project failed', error));
        }
      }
    };

    const addVue = async (): Promise<void> => {
      if (project.value.folder !== '') {
        if (isVueWithStyle.value) {
          await vueService
            .addWithStyle(toProject(project.value))
            .catch(error => logger.error('Adding Vue with style to project failed', error));
        } else {
          await vueService.add(toProject(project.value)).catch(error => logger.error('Adding Vue to project failed', error));
        }
      }
    };

    const addFrontendMavenPlugin = async (): Promise<void> => {
      if (project.value.folder !== '') {
        await projectService
          .addFrontendMavenPlugin(toProject(project.value))
          .catch(error => logger.error('Adding Frontend Maven Plugin to project failed', error));
      }
    };

    const download = async (): Promise<void> => {
      await projectService
        .download(toProject(project.value))
        .then(response => {
          const url = window.URL.createObjectURL(new Blob([response], { type: 'application/zip' }));
          const link = document.createElement('a');
          link.href = url;
          const zipName = project.value.baseName || 'application';
          link.setAttribute('download', zipName + '.zip');
          document.body.appendChild(link);
          link.click();
        })
        .catch(error => logger.error('Downloading project failed', error));
    };

    return {
      project,
      isReactWithStyle,
      isVueWithStyle,
      isSvelteWithStyle,
      language,
      buildTool,
      server,
      client,
      initProject,
      addMaven,
      addJaCoCo,
      addSonarBackend,
      addSonarBackendFrontend,
      addJavaBase,
      addSpringBoot,
      addSpringBootMvcTomcat,
      addSpringBootWebfluxNetty,
      addSpringBootActuator,

      addSpringBootAopLogging,
      addSpringBootLogstash,

      addSpringBootSecurityJWT,
      addSpringBootSecurityJWTBasicAuth,
      addPostgreSQL,
      addMySQL,
      addMariaDB,
      addMongoDB,
      addReact,
      addVue,
      addFrontendMavenPlugin,
      download,
      selectorPrefix,
    };
  },
});
