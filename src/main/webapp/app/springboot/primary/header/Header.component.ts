import { defineComponent } from 'vue';
import { IconVue } from '@/common/primary/icon';

export default defineComponent({
  name: 'Header',

  components: {
    IconVue,
  },

  setup() {
    const selectorPrefix = 'header';

    return {
      selectorPrefix,
    };
  },
});
