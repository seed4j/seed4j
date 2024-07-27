import { AxiosHttp } from '@/shared/http/infrastructure/secondary/AxiosHttp';
import router from '@/router';
import axios from 'axios';
import { createApp } from 'vue';
import App from '@/root/infrastructure/primary/App.vue';

import { provideForAlerts } from '@/shared/alert/application/AlertProvider';
import { provideForLogger } from '@/shared/logger/application/LoggerProvider';
import { provideForModule } from '@/module/application/ModuleProvider';
import { provideWindowsTooling } from '@/injections';
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
