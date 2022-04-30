import ProjectRepository from '@/springboot/secondary/ProjectRepository';
import { AxiosHttp } from '@/http/AxiosHttp';
import router from '@/router';
import axios from 'axios';
import { createApp } from 'vue';
import App from '@/common/primary/app/App.vue';
import AngularRepository from '@/springboot/secondary/client/AngularRepository';
import ReactRepository from '@/springboot/secondary/client/ReactRepository';
import VueRepository from '@/springboot/secondary/client/VueRepository';
import SpringBootRepository from './springboot/secondary/SpringBootRepository';
import ProjectHistoryRepository from '@/common/secondary/ProjectHistoryRepository';
import ConsoleLogger from '@/common/secondary/ConsoleLogger';
import { FileDownloader } from '@/common/primary/FileDownloader';
import { useHistoryStore } from '@/common/primary/HistoryStore';
import { createPinia } from 'pinia';
import piniaPersist from 'pinia-plugin-persist';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap';
import '../content/css/custom.css';

const app = createApp(App);
const pinia = createPinia();
pinia.use(piniaPersist);
app.use(pinia);

const axiosHttp = new AxiosHttp(axios.create({ baseURL: '' }));
const fileDownloader = new FileDownloader(window);
const consoleLogger = new ConsoleLogger(console);
const historyStore = useHistoryStore();
const projectHistoryRepository = new ProjectHistoryRepository(axiosHttp, historyStore);
const projectRepository = new ProjectRepository(axiosHttp, projectHistoryRepository);
const angularRepository = new AngularRepository(axiosHttp, projectHistoryRepository);
const reactRepository = new ReactRepository(axiosHttp, projectHistoryRepository);
const vueRepository = new VueRepository(axiosHttp, projectHistoryRepository);
const springBootRepository = new SpringBootRepository(axiosHttp, projectHistoryRepository);

app.provide('angularService', angularRepository);
app.provide('fileDownloader', fileDownloader);
app.provide('historyStore', historyStore);
app.provide('logger', consoleLogger);
app.provide('projectService', projectRepository);
app.provide('reactService', reactRepository);
app.provide('springBootService', springBootRepository);
app.provide('vueService', vueRepository);
app.use(router);

app.mount('#app');
