import { defineComponent, inject, ref } from 'vue';
import { AuthenticationService } from '@/common/domain/AuthenticationService';
import { jwtStore } from '@/common/domain/JWTStoreService';
import { Logger } from '@/common/domain/Logger';
import { Login } from '@/common/domain/Login';
import { Router } from 'vue-router';

export default defineComponent({
  name: 'Login',
  components: {},
  setup() {
    const authenticationService = inject('authenticationService') as AuthenticationService;
    const logger = inject('logger') as Logger;
    const router = inject('router') as Router;

    const store = jwtStore();

    const form = ref<Login>({
      username: '',
      password: '',
      rememberMe: false,
    });

    let loginError = false;

    const onSubmit = async (): Promise<void> => {
      await authenticationService
        .login(form.value)
        .then((id: string) => {
          store.setToken(id);
          router.push('/');
        })
        .catch(error => {
          loginError = true;
          logger.error('Wrong credentials have been provided', error);
        });
    };

    const getError = (): boolean => {
      return loginError;
    };

    return {
      onSubmit,
      form,
      getError,
    };
  },
});
