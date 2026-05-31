import { heroui } from '@heroui/react';

export const content = [
  './src/main/webapp/index.html',
  './src/main/webapp/**/*.{js,ts,jsx,tsx}',
  './node_modules/@heroui/theme/dist/**/*.{js,ts,jsx,tsx}',
];
export const theme = {
  extend: {},
};
export const darkMode = 'class';
export const plugins = [heroui()];
