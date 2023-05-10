import { defineComponent } from 'vue';
import PhRowVue from './PhRow.vue';
import PhPictureVue from './PhPicture.vue';
import ListPlaceHolderVue from './ListPlaceHolder.vue';

export default defineComponent({
  name: 'LandscapeLoaderVue',
  components: {
    PhRowVue,
    PhPictureVue,
    ListPlaceHolderVue,
  },
});
