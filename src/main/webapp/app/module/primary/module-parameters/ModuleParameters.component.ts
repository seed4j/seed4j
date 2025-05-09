import { defineComponent, PropType } from 'vue';

import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';

import { ModulePropertiesType } from '../ModulePropertiesType';
import { notEmpty } from '../PropertyValue';

export default defineComponent({
  name: 'ModuleParametersVue',
  props: {
    propertiesDefinitions: {
      type: Array as PropType<Array<ModulePropertyDefinition>>,
      required: true,
    },
    moduleSlug: {
      type: String,
      required: true,
    },
    moduleParameters: {
      type: Map<string, ModuleParameterType>,
      required: true,
    },
    propertiesType: {
      type: String as PropType<ModulePropertiesType>,
      required: true,
    },
    selected: {
      type: Boolean,
      required: false,
    },
  },
  setup(props) {
    const typeLabel = (): string => {
      if (mandatoryProperties()) {
        return 'Mandatory';
      }

      return 'Optional';
    };

    const parametersStats = (): string => {
      return `${parametersWithValueCount()} / ${props.propertiesDefinitions.length}`;
    };

    const stateClass = (): string => {
      return `${selectionClass()} ${validityClass()}`;
    };

    const validityClass = (): string => {
      if (parametersWithValueCount() === props.propertiesDefinitions.length) {
        return 'all-valid-parameters';
      }

      if (mandatoryProperties()) {
        return 'invalid-mandatory-parameter';
      }

      return 'invalid-optional-parameter';
    };

    const mandatoryProperties = (): boolean => {
      return props.propertiesType === 'MANDATORY';
    };

    const parametersWithValueCount = (): number => {
      return props.propertiesDefinitions.filter(property => notEmpty(props.moduleParameters.get(property.key))).length;
    };

    const selectionClass = (): string => {
      if (props.selected) {
        return 'selected';
      }

      return 'not-selected';
    };

    return {
      typeLabel,
      parametersStats,
      stateClass,
    };
  },
});
