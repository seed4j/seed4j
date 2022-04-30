import { defineComponent } from 'vue';
import { IconVue } from '@/common/primary/icon';

export default defineComponent({
  name: 'DefaultButton',

  components: {
    IconVue,
  },

  props: {
    label: {
      type: String,
      required: true,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    icon: {
      type: String,
      default: undefined,
    },
  },
});
