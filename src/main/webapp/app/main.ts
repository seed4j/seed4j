import { AxiosHttp } from '@/http/AxiosHttp';
import router from '@/router';
import axios from 'axios';
import { createApp } from 'vue';
import App from '@/common/primary/app/App.vue';
import ConsoleLogger from '@/common/secondary/ConsoleLogger';
import { MittAlertBus } from '@/common/secondary/alert/MittAlertBus';
import mitt from 'mitt';

import { MittAlertListener } from '@/common/secondary/alert/MittAlertListener';
import { RestModulesRepository } from './module/secondary/RestModulesRepository';
import { RestProjectFoldersRepository } from '@/module/secondary/RestProjectFoldersRepository';
import { RestManagementRepository } from '@/module/secondary/RestManagementRepository';
import { WindowApplicationListener } from './common/primary/applicationlistener/WindowApplicationListener';
import { Timeout } from '@/common/primary/timeout/Timeout';
import { BodyCursorUpdater } from '@/common/primary/cursor/BodyCursorUpdater';
import { LandscapeScroller } from '@/module/primary/landscape/LandscapeScroller';
import { LocalWindowThemeRepository } from './module/secondary/LocalWindowThemeRepository';
import { LocalStorageModuleParametersRepository } from './module/secondary/LocalStorageModuleParametersRepository';

const app = createApp(App);

const emitter = mitt();
const alertBus = new MittAlertBus(emitter);
const alertListener = new MittAlertListener(emitter);
const axiosHttp = new AxiosHttp(axios.create({ baseURL: '' }));
const consoleLogger = new ConsoleLogger(console);
const cursorUpdater = new BodyCursorUpdater(window);
const landscapeScroller = new LandscapeScroller();
const modulesRepository = new RestModulesRepository(axiosHttp);
const projectFoldersRepository = new RestProjectFoldersRepository(axiosHttp);
const managementRepository = new RestManagementRepository(axiosHttp);
const applicationListener = new WindowApplicationListener(window);
const localWindowThemeRepository = new LocalWindowThemeRepository(window, localStorage);
const moduleParametersRepository = new LocalStorageModuleParametersRepository(localStorage);
const timeout = () => new Timeout();

app.provide('alertBus', alertBus);
app.provide('alertListener', alertListener);
app.provide('cursorUpdater', cursorUpdater);
app.provide('globalWindow', window);
app.provide('logger', consoleLogger);
app.provide('landscapeScroller', landscapeScroller);
app.provide('modules', modulesRepository);
app.provide('projectFolders', projectFoldersRepository);
app.provide('management', managementRepository);
app.provide('applicationListener', applicationListener);
app.provide('themeRepository', localWindowThemeRepository);
app.provide('moduleParameters', moduleParametersRepository);
app.provide('timeout', timeout);
app.use(router);

app.mount('#app');
