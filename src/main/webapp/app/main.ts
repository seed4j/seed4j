import { createApp } from 'vue';
import App from './common/primary/app/App.vue';
import router from './router';

createApp(App).use(router).mount('#app');
