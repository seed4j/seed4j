import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: 'integration',
  testMatch: '**/*.spec.ts',
  forbidOnly: !!process.env.CI,
  reporter: [['list'], ['html', { outputFolder: '../../../../target/playwright/e2e-report', open: 'never' }]],
  outputDir: '../../../../target/playwright/e2e-results',
  use: {
    baseURL: 'http://localhost:1339',
    trace: 'retain-on-failure',
  },
  projects: [{ name: 'chromium', use: { ...devices['Desktop Chrome'] } }],
});
