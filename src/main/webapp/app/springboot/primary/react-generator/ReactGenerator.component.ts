import { defineComponent, inject, ref } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { Logger } from '@/common/domain/Logger';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { DefaultButtonVue } from '@/common/primary/default-button';

export default defineComponent({
  name: 'ReactGeneratorComponent',

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
    const reactService = inject('reactService') as ReactService;

    const selectorPrefix = 'react-generator';
    const isReactWithStyle = ref<boolean>(false);

    const addReact = async (): Promise<void> => {
      if (props.project.folder !== '') {
        if (isReactWithStyle.value) {
          await reactService
            .addWithStyle(toProject(props.project as ProjectToUpdate))
            .catch(error => logger.error('Adding React with style to project failed', error));
        } else {
          await reactService
            .add(toProject(props.project as ProjectToUpdate))
            .catch(error => logger.error('Adding React to project failed', error));
        }
      }
    };

    return {
      selectorPrefix,
      isReactWithStyle,
      addReact,
      props,
    };
  },
});
