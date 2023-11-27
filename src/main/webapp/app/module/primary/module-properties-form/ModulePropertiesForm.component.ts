import { ModuleParameter } from '@/module/domain/ModuleParameter';
import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { ModulePropertyKey } from '@/module/domain/ModulePropertyKey';
import { defineComponent, PropType } from 'vue';
import { empty } from '../PropertyValue';

export default defineComponent({
  name: 'ModulePropertiesFormVue',
  props: {
    folderPath: {
      type: String,
      required: true,
    },
    properties: {
      type: Array as PropType<Array<ModulePropertyDefinition>>,
      required: true,
    },
    parameters: {
      type: Object as PropType<Map<ModulePropertyKey, ModuleParameterType>>,
      required: true,
    },
  },
  emits: ['moduleCommitUpdated', 'folderPathUpdated', 'folderPathSelected', 'propertyUpdated', 'propertyDeleted'],
  setup(_, { emit }) {
    const updateModuleCommit = (commitModule: boolean): void => {
      emit('moduleCommitUpdated', commitModule);
    };

    const updateFolderPath = (folderPath: string): void => {
      emit('folderPathUpdated', folderPath);
    };

    const folderPathSelected = (): void => {
      emit('folderPathSelected');
    };

    const setStringProperty = (key: string, value: string): void => {
      emit('propertyUpdated', property(key, value));
    };

    const setNumberProperty = (key: string, value: string): void => {
      if (empty(value)) {
        emit('propertyDeleted', key);
      } else {
        emit('propertyUpdated', property(key, +value));
      }
    };

    const propertyUpdates: Record<string, PropertyUpdate> = {
      'false': new FalsePropertyUpdate(),
      'true': new TruePropertyUpdate(),
      'default': new DeletePropertyUpdate(),
    };

    const setBooleanProperty = (key: string, value: string): void => {
      const update = propertyUpdates[value] || propertyUpdates['default'];
      update.applyUpdate(key);
    };

    const property = (key: string, value: ModuleParameterType): ModuleParameter => ({
      key,
      value,
    });

    return {
      updateModuleCommit,
      updateFolderPath,
      folderPathSelected,
      setStringProperty,
      setNumberProperty,
      setBooleanProperty,
    };
  },
});

abstract class PropertyUpdate {
  abstract applyUpdate(key: string): void;
}

    // Concrete class for updating a boolean property to true
class TruePropertyUpdate extends PropertyUpdate {
  applyUpdate(key: string): void {
    emit('propertyUpdated', property(key, true));
  }
}

    // Concrete class for updating a boolean property to false
class FalsePropertyUpdate extends PropertyUpdate {
  applyUpdate(key: string): void {
    emit('propertyUpdated', property(key, false));
  }
}

    // Concrete class for deleting a property
class DeletePropertyUpdate extends PropertyUpdate {
  applyUpdate(key: string): void {
    emit('propertyDeleted', key);
  }
}
