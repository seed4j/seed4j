/// <reference types="vitest" />

import { defineConfig } from 'vite';
import tsconfigPaths from 'vite-tsconfig-paths';
import react from '@vitejs/plugin-react';
import path from 'path';

export default defineConfig({
  plugins: [react(), tsconfigPaths()],
  resolve: {
    alias: [{ find: '@', replacement: path.resolve(__dirname, 'src/main/webapp/app') }],
  },
  test: {
    reporters: ['verbose', 'vitest-sonar-reporter'],
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
    exclude: ['node_modules', 'src/test/javascript/integration/**/*.spec.ts'],
    coverage: {
      all: true,
      thresholds: {
        perFile: true,
        autoUpdate: true,
        100: true,
        lines: 100,
        functions: 100,
        branches: 100,
        statements: 100,
      },
      include: ['src/main/webapp/app/**/*.ts', 'src/main/webapp/app/**/*.tsx'],
      exclude: ['src/main/webapp/app/index.tsx', 'src/main/webapp/app/**/*.d.ts'],
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
