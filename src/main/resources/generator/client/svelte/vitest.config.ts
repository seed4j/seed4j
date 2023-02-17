/// <reference types="vitest" />

import { defineConfig } from 'vitest/config';
import { svelte } from "@sveltejs/vite-plugin-svelte";
import path from 'path';

export default defineConfig({
  plugins: [svelte()],
  resolve: {
    alias: [
      { find: '@', replacement: path.resolve(__dirname, 'src/main/webapp/app') },
      { find: '@assets', replacement: path.resolve(__dirname, 'src/main/webapp/assets') },
    ],
  },
  test: {
    reporters: ['json', 'verbose', 'vitest-sonar-reporter'],
    outputFile: {
      'vitest-sonar-reporter': 'target/test-results/TESTS-results-sonar.xml',
    },
    globals: true,
    logHeapUsage: true,
    minThreads: 1,
    maxThreads: 2,
    environment: 'jsdom',
    cache: false,
    include: ['src/test/javascript/spec/**/*.(spec|test).(ts|tsx)'],
    exclude: ['node_modules'],
    coverage: {
      statements: 100,
      branches: 100,
      functions: 100,
      lines: 100,
      clean: true,
      provider: 'istanbul',
      reportsDirectory: 'target/test-results/',
      reporter: ['html', 'json-summary', 'text', 'text-summary', 'lcov', 'clover'],
      watermarks: {
        statements: [100, 100],
        branches: [100, 100],
        functions: [100, 100],
        lines: [100, 100],
      },
    },
  },
});
