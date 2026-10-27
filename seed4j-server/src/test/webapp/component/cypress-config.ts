import registerCodeCoverageTasks from '@cypress/code-coverage/task';
import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:9000',
    specPattern: 'src/test/webapp/component/integration/**/*.spec.ts',
    fixturesFolder: 'src/test/webapp/component/fixtures',
    supportFile: 'src/test/webapp/component/support/component-tests.ts',
    video: false,
    setupNodeEvents(on, config) {
      registerCodeCoverageTasks(on, config);

      return config;
    },
  },
});
