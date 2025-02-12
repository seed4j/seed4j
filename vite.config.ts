import vue from '@vitejs/plugin-vue';
import * as path from 'path';
import { defineConfig } from 'vite';
import istanbul from 'vite-plugin-istanbul';

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src/main/webapp/app'),
    },
  },
  plugins: [
    vue({
      template: {
        compilerOptions: {
          isCustomElement: tag => /^x-/.test(tag),
        },
      },
    }),
    istanbul(),
  ],
  build: {
    outDir: '../../../target/classes/static',
    sourcemap: true,
  },
  root: 'src/main/webapp',
  server: {
    port: 9000,
    proxy: {
      '/api': {
        ws: true,
        changeOrigin: true,
        target: 'http://localhost:7471',
      },
      '/management': {
        ws: true,
        changeOrigin: true,
        target: 'http://localhost:7471',
      },
      '/style': {
        ws: true,
        changeOrigin: true,
        rewrite: path => path.replace(/^\/style/, ''),
        target: 'http://localhost:9005',
      },
      '/reload': {
        ws: true,
        changeOrigin: true,
        target: 'http://localhost:9005',
      },
    },
  },
});
