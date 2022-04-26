import { defineComponent, inject, ref } from 'vue';
import { AngularService } from '@/springboot/domain/client/AngularService';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { Logger } from '@/common/domain/Logger';

export default defineComponent({
  name: 'AngularGeneratorComponent',

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
    const isAngularWithStyle = ref<boolean>(false);

    const addAngular = async (): Promise<void> => {
      if (props.project.folder !== '') {
        if (isAngularWithStyle.value) {
          await angularService
            .addWithStyle(toProject(props.project as ProjectToUpdate))
            .catch(error => logger.error('Adding Angular with style to project failed', error));
        } else {
          await angularService
            .add(toProject(props.project as ProjectToUpdate))
            .catch(error => logger.error('Adding Angular to project failed', error));
        }
      }
    };

    return {
      selectorPrefix,
      isAngularWithStyle,
      addAngular,
      props,
    };
  },
});
