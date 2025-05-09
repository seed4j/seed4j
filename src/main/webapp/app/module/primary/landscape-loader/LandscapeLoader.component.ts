import { defineComponent } from 'vue';

import ListPlaceHolderVue from './ListPlaceHolder.vue';
import PhPictureVue from './PhPicture.vue';
import PhRowVue from './PhRow.vue';

export default defineComponent({
  name: 'LandscapeLoaderVue',
  components: {
    PhRowVue,
    PhPictureVue,
    ListPlaceHolderVue,
  },
});
