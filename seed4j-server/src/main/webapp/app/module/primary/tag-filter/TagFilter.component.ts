import { defineComponent } from 'vue';

export default defineComponent({
  name: 'TagFilterVue',
  props: {
    tag: {
      type: String,
      required: true,
    },
    isSelected: {
      type: Boolean,
      required: false,
    },
  },
  emits: ['selected'],
  setup(props, { emit }) {
    const selectionClass = (): string => {
      if (props.isSelected) {
        return 'selected';
      }

      return 'not-selected';
    };

    const select = (): void => {
      emit('selected');
    };

    return {
      selectionClass,
      select,
    };
  },
});
