import { sveltekit } from '@sveltejs/kit/vite';
import path from 'path';

const config = {
  plugins: [sveltekit()],
  resolve: {
    alias: [
      { find: '@', replacement: path.resolve('src/main/webapp') },
      { find: '@assets', replacement: path.resolve('src/main/webapp/assets') },
    ],
  },
};

export default config;
