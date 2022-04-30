import { defineComponent, ref } from 'vue';
import { DefaultButtonVue } from '@/common/primary/default-button';

export default defineComponent({
  name: 'SvelteGeneratorComponent',

  components: {
    DefaultButtonVue,
  },

  props: {
    project: {
      type: Object,
      required: true,
    },
  },

  setup() {
    const selectorPrefix = 'svelte-generator';
    const isSvelteWithStyle = ref<boolean>(false);

    return {
      selectorPrefix,
      isSvelteWithStyle,
    };
  },
});
