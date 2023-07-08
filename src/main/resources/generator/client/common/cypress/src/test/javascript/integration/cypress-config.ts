import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:9000',
    specPattern: 'src/test/javascript/integration/**/*.spec.ts',
    fixturesFolder: false,
    supportFile: false,
    video: false,
  },
});
