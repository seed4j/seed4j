import { expect, test } from '@playwright/test';

import { dataSelector } from '../support/Selector';

test.describe('Patch', () => {
  test('should display modules', async ({ page }) => {
    await page.goto('/patches');

    await expect(page.locator(dataSelector('modules-list'))).toBeVisible();
  });
});
