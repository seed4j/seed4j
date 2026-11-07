import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: 'integration',
  testMatch: '**/*.spec.ts',
  forbidOnly: !!process.env.CI,
  // The first test of each spec loads the instrumented application: it takes 3 to 4 seconds on CI runners, too close to
  // the default 5 seconds
  expect: { timeout: 10_000 },
  globalSetup: './support/CoverageSetup.ts',
  reporter: [['list'], ['html', { outputFolder: '../../../../target/playwright/component-report', open: 'never' }]],
  outputDir: '../../../../target/playwright/component-results',
  use: {
    baseURL: 'http://localhost:9000',
    trace: 'retain-on-failure',
  },
  projects: [{ name: 'chromium', use: { ...devices['Desktop Chrome'] } }],
  webServer: [
    {
      command: 'npm run dev:vite',
      cwd: '../../../..',
      url: 'http://localhost:9000',
      reuseExistingServer: !process.env.CI,
    },
    {
      // Vite proxies /style to the stylesheets served by Tikui
      command: 'npm run dev:tikui',
      cwd: '../../../..',
      url: 'http://localhost:9005/tikui.css',
      reuseExistingServer: !process.env.CI,
    },
  ],
});
