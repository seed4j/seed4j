import { defineComponent, inject } from 'vue';
import { AngularService } from '@/springboot/domain/client/AngularService';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { Logger } from '@/common/domain/Logger';
import { DefaultButtonVue } from '@/common/primary/default-button';

export default defineComponent({
  name: 'AngularGeneratorComponent',

  components: {
    DefaultButtonVue,
  },

  props: {
    project: {
      type: Object,
      required: true,
    },
  },

  setup(props) {
    const logger = inject('logger') as Logger;
    const angularService = inject('angularService') as AngularService;

    const selectorPrefix = 'angular-generator';

    const addAngular = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await angularService
          .add(toProject(props.project as ProjectToUpdate))
          .catch(error => logger.error('Adding Angular to project failed', error));
      }
    };

    return {
      selectorPrefix,
      addAngular,
      props,
    };
  },
});
