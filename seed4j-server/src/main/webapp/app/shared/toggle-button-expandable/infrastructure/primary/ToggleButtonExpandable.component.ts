import { defineComponent } from 'vue';

export default defineComponent({
  name: 'ToggleButtonExpandable',
  props: {
    shortName: {
      type: String,
      required: true,
    },
    fullName: {
      type: String,
      required: true,
    },
    isActive: {
      type: Boolean,
      default: false,
    },
    customClasses: {
      type: Array as () => string[],
      default: () => [],
    },
    dataTestId: {
      type: String,
      default: '',
    },
    title: {
      type: String,
      default: '',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['click', 'mouseenter', 'mouseleave'],
  setup(props, { emit }) {
    const buttonClasses = (): string => {
      return [...props.customClasses, props.isActive ? '-active' : ''].filter(Boolean).join(' ');
    };

    const handleClick = (): void => emit('click');

    return {
      buttonClasses,
      handleClick,
    };
  },
});
