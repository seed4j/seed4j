import { defineComponent, PropType } from 'vue';

import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { IconVue } from '@/shared/icon/infrastructure/primary';

export default defineComponent({
  name: 'LandscapeComponentVue',
  components: { IconVue },
  props: {
    module: {
      type: Object as PropType<LandscapeModule>,
      required: true,
    },
    landscapeElements: {
      type: Object as PropType<Map<string, HTMLElement>>,
      required: true,
    },
    moduleFlavor: {
      type: String,
      required: true,
    },
  },
  emits: ['over', 'out', 'clicked', 'apply'],
  setup(props, { emit }) {
    const moduleClass = (): string => {
      return props.moduleFlavor;
    };

    const emphasizeModule = (): void => {
      emit('over');
    };

    const deEmphasizeModule = (): void => {
      emit('out');
    };

    const clickedModule = (): void => {
      emit('clicked');
    };

    const applyModule = (): void => {
      emit('apply');
    };

    const isAppliedModule = (): boolean => {
      return moduleClass().includes('applied');
    };

    return {
      moduleClass,
      emphasizeModule,
      deEmphasizeModule,
      clickedModule,
      applyModule,
      isAppliedModule,
    };
  },
});
