import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:9000',
    specPattern: 'src/test/webapp/component/integration/**/*.spec.ts',
    fixturesFolder: 'src/test/webapp/component/fixtures',
    supportFolder: 'src/test/webapp/component/support',
    supportFile: false,
    video: false,
  },
});
