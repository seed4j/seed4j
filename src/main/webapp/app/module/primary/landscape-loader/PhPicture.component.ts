import { defineComponent } from 'vue';

export default defineComponent({
  name: 'PhPictureVue',
  props: {
    count: {
      type: Number,
      required: true,
    },
  },
});
