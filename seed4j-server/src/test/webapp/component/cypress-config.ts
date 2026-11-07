import registerCodeCoverageTasks from '@cypress/code-coverage/task';
import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:9000',
    specPattern: 'src/test/webapp/component/integration/**/*.spec.ts',
    fixturesFolder: 'src/test/webapp/component/fixtures',
    supportFile: 'src/test/webapp/component/support/component-tests.ts',
    video: false,
    // The first test of each spec loads the instrumented application in a new browser: it takes 3 to 4 seconds on CI
    // runners, too close to the default 4 seconds
    defaultCommandTimeout: 10_000,
    setupNodeEvents(on, config) {
      registerCodeCoverageTasks(on, config);

      return config;
    },
  },
});
