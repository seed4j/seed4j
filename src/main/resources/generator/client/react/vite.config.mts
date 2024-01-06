import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

export default defineConfig({
  plugins: [react()],
  build: {
    outDir: '../../../target/classes/static',
  },
  resolve: {
    alias: [
      { find: '@', replacement: path.resolve(__dirname, 'src/main/webapp/app') },
      { find: '@assets', replacement: path.resolve('src/main/webapp/assets') },
    ],
  },
  root: 'src/main/webapp',
  server: {
    port: 9000,
    hmr: { overlay: false },
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
  define: {
    'process.env': {},
  },
});
