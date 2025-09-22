import { IconVue } from '@/shared/icon/infrastructure/primary';
import { PropType, Ref, defineComponent, onMounted, ref } from 'vue';

export default defineComponent({
  name: 'LandscapeMiniMapComponentVue',
  components: { IconVue },
  props: {
    landscapeContainer: {
      type: Object as PropType<HTMLDivElement>,
      required: true,
    },
  },
  setup({ landscapeContainer }) {
    const minimapHTML = ref('');
    const realScale = ref(1);

    const isMiniMapOpen = ref(false);

    const minimapContainer = ref<HTMLElement>(document.createElement('div'));
    const minimapSize = ref<HTMLElement>(document.createElement('div'));
    const minimapViewer = ref<HTMLElement>(document.createElement('div'));
    const minimapContent = ref<HTMLElement>(document.createElement('div'));

    const isMoving: Ref<boolean> = ref(false);
    const startX: Ref<number> = ref(0);
    const startY: Ref<number> = ref(0);

    const observer = new MutationObserver(() => {
      minimapHTML.value = removeDataSelectorAttrs(landscapeContainer.outerHTML);
    });
    observer.observe(landscapeContainer, { attributes: true, childList: true, subtree: true });

    onMounted(() => {
      minimapHTML.value = removeDataSelectorAttrs(landscapeContainer.outerHTML);

      setupDimensions();

      landscapeContainer.addEventListener('scroll', trackScroll);
      window.addEventListener('resize', setupDimensions);

      closeMiniMap();
    });

    const removeDataSelectorAttrs = (data: string): string => {
      const regex = /data-testid="[^"]*"/g;
      return data.replaceAll(regex, '');
    };

    const getRealScale = (): number => {
      return realScale.value;
    };

    const initRealScale = (): void => {
      realScale.value = minimapContainer.value.clientWidth / landscapeContainer.scrollWidth;
    };

    const setupDimensions = (): void => {
      const bodyRatio = landscapeContainer.scrollHeight / landscapeContainer.scrollWidth;

      minimapContainer.value.style.width = '13%';

      initRealScale();

      minimapViewer.value.style.width = landscapeContainer.clientWidth * getRealScale() + 'px';

      minimapSize.value.style.paddingTop = bodyRatio * 100 + '%';

      minimapViewer.value.style.paddingTop = landscapeContainer.clientHeight * getRealScale() + 'px';

      minimapContent.value.style.transform = 'scale(' + getRealScale() + ')';
      minimapContent.value.style.width = 100 / getRealScale() + '%';
      minimapContent.value.style.height = 100 / getRealScale() + '%';
    };

    const startGrabbing = (mouseEvent: MouseEvent): void => {
      isMoving.value = true;

      startX.value = mouseEvent.clientX;
      startY.value = mouseEvent.clientY;
    };

    const stopGrabbing = (): void => {
      isMoving.value = false;
    };

    const calculateScrollPosition = (scrollX: number, scrollY: number): { x: number; y: number } => {
      return {
        x: landscapeContainer.scrollLeft - scrollX / getRealScale(),
        y: landscapeContainer.scrollTop - scrollY / getRealScale(),
      };
    };
    const grabbing = (mouseEvent: MouseEvent): void => {
      if (!isMoving.value) {
        return;
      }

      const scrollX = startX.value - mouseEvent.clientX;
      const scrollY = startY.value - mouseEvent.clientY;

      startX.value = mouseEvent.clientX;
      startY.value = mouseEvent.clientY;

      const { x, y } = calculateScrollPosition(scrollX, scrollY);

      landscapeContainer.scroll(x, y);
    };

    const trackScroll = () => {
      const translateX = `translateX(${landscapeContainer.scrollLeft * getRealScale()}px)`;
      const translateY = `translateY(${landscapeContainer.scrollTop * getRealScale()}px)`;

      minimapViewer.value.style.transform = `${translateX} ${translateY}`;
    };

    const openMiniMap = (): void => {
      minimapSize.value.style.display = 'block';
      isMiniMapOpen.value = true;
    };

    const closeMiniMap = (): void => {
      minimapSize.value.style.display = 'none';
      isMiniMapOpen.value = false;
    };

    return {
      minimapHTML,
      minimapContainer,
      minimapSize,
      minimapViewer,
      minimapContent,
      startGrabbing,
      stopGrabbing,
      grabbing,
      openMiniMap,
      closeMiniMap,
      isMiniMapOpen,
    };
  },
});
