import vue from '@vitejs/plugin-vue';
import * as path from 'path';
import { defineConfig, type Plugin } from 'vite';
import istanbul from 'vite-plugin-istanbul';

type TransformHook = Exclude<Plugin['transform'], { handler: unknown } | undefined>;

// vite-plugin-istanbul v9 instruments Vue SFC script chunks with an approximate source map (every mapping at column 0).
// For `<script src="./X.component.ts">`, coverage of X.component.ts then no longer matches the one of unit tests and
// statements are counted twice when merged. Instrument these external scripts as plain TS modules instead.
// See https://github.com/iFaxity/vite-plugin-istanbul/issues/435
const istanbulWithExternalVueScripts = (): Plugin => {
  const plugin = istanbul();
  const transform = plugin.transform as TransformHook;
  return {
    ...plugin,
    transform(code, id, options) {
      return transform.call(this, code, id.replace(/\?vue&type=script&src=true.*$/, ''), options);
    },
  };
};

// https://vite.dev/config/
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
          isCustomElement: tag => tag.startsWith('x-'),
        },
      },
    }),
    istanbulWithExternalVueScripts(),
  ],
  build: {
    outDir: '../../../target/classes/static',
  },
  root: 'src/main/webapp',
  // Keep the cache out of node_modules: `tikui-core serve` runs `sass --watch -I node_modules`,
  // which crashes when Vite creates then renames its deps_temp_* folder there.
  // See https://github.com/tikui/tikui-core/issues/602
  cacheDir: path.resolve(__dirname, 'target/vite-cache'),
  server: {
    port: 9000,
    proxy: {
      '/api': {
        ws: true,
        changeOrigin: true,
        target: 'http://localhost:1339',
      },
      '/management': {
        ws: true,
        changeOrigin: true,
        target: 'http://localhost:1339',
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
