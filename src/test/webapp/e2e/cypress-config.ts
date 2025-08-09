import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:1339',
    specPattern: 'src/test/webapp/e2e/integration/**/*.spec.ts',
    fixturesFolder: 'src/test/e2e/cypress/fixtures',
    supportFolder: 'src/test/e2e/cypress/support',
    supportFile: false,
    video: false,
  },
});
