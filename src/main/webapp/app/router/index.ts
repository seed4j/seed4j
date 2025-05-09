import { createRouter, createWebHistory } from 'vue-router';

import { LandscapeVue } from '@/module/primary/landscape';
import { ModulesVue } from '@/module/primary/modules-patch';

export const routes = [
  {
    path: '/',
    redirect: { name: 'landscape' },
  },
  {
    path: '/patches',
    name: 'module-patches',
    component: ModulesVue,
  },
  {
    path: '/landscape',
    name: 'landscape',
    component: LandscapeVue,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
