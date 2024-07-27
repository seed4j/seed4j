import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { IconVue } from '@/shared/icon/infrastructure/primary';
import { defineComponent, PropType } from 'vue';

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

    const emphasisizeModule = (): void => {
      emit('over');
    };

    const deEmphasisizeModule = (): void => {
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
      emphasisizeModule,
      deEmphasisizeModule,
      clickedModule,
      applyModule,
      isAppliedModule,
    };
  },
});
