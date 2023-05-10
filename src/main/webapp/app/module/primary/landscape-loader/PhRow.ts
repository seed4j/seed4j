import { defineComponent } from 'vue';

export default defineComponent({
  name: 'PhRowVue',
  props: {
    count: {
      type: Number,
      required: true,
    },
  },
});
