import App from '@/root/infrastructure/primary/App.vue';
import router from '@/router';
import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import axios from 'axios';
import { createApp } from 'vue';

import { provideWindowsTooling } from '@/injections';
import { provideForModule } from '@/module/application/ModuleProvider';
import { provideForAlerts } from '@/shared/alert/application/AlertProvider';
import { provideForLogger } from '@/shared/logger/application/LoggerProvider';
import { provideForToast } from '@/shared/toast/application/ToastProvider';

const app = createApp(App);

const axiosHttp = new AxiosHttp(axios.create({ baseURL: '' }));

provideForAlerts();
provideForLogger();
provideForModule(axiosHttp);
provideWindowsTooling();
provideForToast();

app.use(router);

app.mount('#app');
