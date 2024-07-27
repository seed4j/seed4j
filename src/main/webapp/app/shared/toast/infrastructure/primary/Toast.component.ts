import { defineComponent, onBeforeUnmount, onMounted, ref, Ref } from 'vue';
import { Unsubscribe } from '@/shared/alert/domain/Unsubscribe';
import { ToastType } from '@/shared/toast/infrastructure/primary/ToastType';
import { ToastMessage } from '@/shared/toast/infrastructure/primary/ToastMessage';
import { AlertMessage } from '@/shared/alert/domain/AlertMessage';
import { IconVue } from '@/shared/icon/infrastructure/primary';
import { inject } from '@/injections';
import { ALERT_LISTENER } from '@/shared/alert/application/AlertProvider';
import { TIMEOUT } from '@/shared/toast/application/ToastProvider';

const WORD_TIME_MS = 300;
const TOAST_ATTENTION_MS = 1500;

const wordCount = (message: string): number => message.trim().split(/\s+/).length;

const wordsToMs = (message: ToastMessage): number => wordCount(message) * WORD_TIME_MS + TOAST_ATTENTION_MS;

export default defineComponent({
  name: 'Toast',

  components: {
    IconVue,
  },

  setup() {
    const alertListener = inject(ALERT_LISTENER);
    const toastTimeout = inject(TIMEOUT);

    const toast = ref(null);
    const show = ref(false);
    const type: Ref<ToastType | undefined> = ref(undefined);
    const message: Ref<ToastMessage> = ref('');

    let unsubscribeSuccessAlertBus!: Unsubscribe;
    let unsubscribeErrorAlertBus!: Unsubscribe;

    const hideToast = () => {
      show.value = false;
      toastTimeout.unregister();
    };

    const showToast = () => {
      show.value = true;
      toastTimeout.register(() => hideToast(), wordsToMs(message.value));
    };

    const displaySuccess = (alertMessage: AlertMessage) => {
      type.value = ToastType.SUCCESS;
      message.value = alertMessage;
      showToast();
    };

    const displayError = (alertMessage: AlertMessage) => {
      type.value = ToastType.ERROR;
      message.value = alertMessage;
      showToast();
    };

    onMounted(() => {
      unsubscribeSuccessAlertBus = alertListener.onSuccess(displaySuccess);
      unsubscribeErrorAlertBus = alertListener.onError(displayError);
    });

    onBeforeUnmount(() => {
      unsubscribeSuccessAlertBus();
      unsubscribeErrorAlertBus();
    });

    return {
      toast,
      type,
      message,
      hideToast,
      show,
    };
  },
  methods: {
    close() {
      this.hideToast();
    },
  },
});
