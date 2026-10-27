import { defineComponent, onMounted, ref } from 'vue';

import { inject } from '@/injections';
import { MODULES_REPOSITORY } from '@/module/application/ModuleProvider';
import { Preset } from '@/module/domain/Preset';
import { IconVue } from '@/shared/icon/infrastructure/primary';

export default defineComponent({
  name: 'LandscapePresetConfigurationVue',
  components: { IconVue },
  props: {
    selectedPresetName: {
      type: String,
      default: '',
    },
  },
  emits: ['selected'],
  setup(_props, { emit }) {
    const presets = ref<Preset[]>([]);
    const modules = inject(MODULES_REPOSITORY);

    const isPresetConfigurationOpen = ref(true);

    onMounted(() => {
      modules
        .preset()
        .then(response => {
          presets.value = response.presets;
        })
        .catch(error => console.error(error));
    });

    const handlePresetChange = (event: Event) => {
      const selectedValue = (event.target as HTMLSelectElement).value;
      if (selectedValue === '') {
        emit('selected', null);
      } else {
        const selectedPreset = presets.value.find(preset => preset.name === selectedValue);
        emit('selected', selectedPreset);
      }
    };

    const openPresetConfiguration = (): void => {
      isPresetConfigurationOpen.value = true;
    };

    const closePresetConfiguration = (): void => {
      isPresetConfigurationOpen.value = false;
    };

    return {
      presets,
      handlePresetChange,
      isPresetConfigurationOpen,
      openPresetConfiguration,
      closePresetConfiguration,
    };
  },
});
