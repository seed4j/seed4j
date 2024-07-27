import { defineComponent, inject, ref } from 'vue';
import { IconVue } from '@/shared/icon/infrastructure/primary';
import { ThemeButtonVue } from '@/shared/theme-button/infrastructure/primary';
import { ManagementRepository } from '@/module/domain/ManagementRepository';
import { ManagementInfo } from '@/module/domain/ManagementInfo';

export default defineComponent({
  name: 'Header',

  components: {
    IconVue,
    ThemeButtonVue,
  },

  setup() {
    const management = inject('management') as ManagementRepository;
    const selectorPrefix = 'header';
    const version = ref('');

    management
      .getInfo()
      .then((info: ManagementInfo) => {
        version.value = info?.git?.build?.version;
      })
      .catch(error => console.error(error));

    return {
      selectorPrefix,
      version,
    };
  },
});
