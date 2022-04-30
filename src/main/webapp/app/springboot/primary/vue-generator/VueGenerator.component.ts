import { defineComponent, inject, ref } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { Logger } from '@/common/domain/Logger';
import { VueService } from '@/springboot/domain/client/VueService';
import { DefaultButtonVue } from '@/common/primary/default-button';

export default defineComponent({
  name: 'VueGeneratorComponent',

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
    const vueService = inject('vueService') as VueService;

    const selectorPrefix = 'vue-generator';
    const isVueWithStyle = ref<boolean>(false);

    const addVue = async (): Promise<void> => {
      if (props.project.folder !== '') {
        if (isVueWithStyle.value) {
          await vueService
            .addWithStyle(toProject(props.project as ProjectToUpdate))
            .catch(error => logger.error('Adding Vue with style to project failed', error));
        } else {
          await vueService
            .add(toProject(props.project as ProjectToUpdate))
            .catch(error => logger.error('Adding Vue to project failed', error));
        }
      }
    };

    return {
      selectorPrefix,
      isVueWithStyle,
      addVue,
      props,
    };
  },
});
