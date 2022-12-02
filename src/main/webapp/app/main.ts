import { AxiosHttp } from '@/http/AxiosHttp';
import router from '@/router';
import axios from 'axios';
import { createApp } from 'vue';
import App from '@/common/primary/app/App.vue';
import ConsoleLogger from '@/common/secondary/ConsoleLogger';
import { MittAlertBus } from '@/common/secondary/alert/MittAlertBus';
import mitt from 'mitt';

import '../content/css/custom.css';
import { MittAlertListener } from '@/common/secondary/alert/MittAlertListener';
import { RestModulesRepository } from './module/secondary/RestModulesRepository';
import { RestStatisticsRepository } from './common/secondary/RestStatisticsRepository';
import { WindowApplicationListener } from './common/primary/applicationlistener/WindowApplicationListener';
import { Timeout } from '@/common/primary/timeout/Timeout';
import { RestStoreProjectFolderRepository } from '@/module/secondary/RestStoreProjectFolderRepository';

const app = createApp(App);

const emitter = mitt();
const alertBus = new MittAlertBus(emitter);
const alertListener = new MittAlertListener(emitter);
const axiosHttp = new AxiosHttp(axios.create({ baseURL: '' }));
const consoleLogger = new ConsoleLogger(console);
const modulesRepository = new RestModulesRepository(axiosHttp);
const projectFoldersRepository = new RestStoreProjectFolderRepository(localStorage, axiosHttp);
const statisticsRepository = new RestStatisticsRepository(axiosHttp);
const applicationListener = new WindowApplicationListener(window);
const timeout = () => new Timeout();

app.provide('alertBus', alertBus);
app.provide('alertListener', alertListener);
app.provide('globalWindow', window);
app.provide('logger', consoleLogger);
app.provide('modules', modulesRepository);
app.provide('statistics', statisticsRepository);
app.provide('projectFolders', projectFoldersRepository);
app.provide('applicationListener', applicationListener);
app.provide('timeout', timeout);
app.use(router);

app.mount('#app');
