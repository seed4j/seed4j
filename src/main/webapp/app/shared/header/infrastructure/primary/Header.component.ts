import { defineComponent, ref } from 'vue';

import { inject } from '@/injections';
import { MANAGEMENT_REPOSITORY } from '@/module/application/ModuleProvider';
import { ManagementInfo } from '@/module/domain/ManagementInfo';
import { IconVue } from '@/shared/icon/infrastructure/primary';
import { ThemeButtonVue } from '@/shared/theme-button/infrastructure/primary';

export default defineComponent({
  name: 'Header',

  components: {
    IconVue,
    ThemeButtonVue,
  },

  setup() {
    const management = inject(MANAGEMENT_REPOSITORY);
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
