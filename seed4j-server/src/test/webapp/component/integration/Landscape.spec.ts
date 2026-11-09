import { expect, test } from '../support/Coverage';
import { fixture, interceptForever } from '../support/Interceptor';
import { dataSelector } from '../support/Selector';

test.describe('Landscape', () => {
  test.beforeEach(async ({ page }) => {
    await page.route('**/api/project-folders', route => route.fulfill({ body: '/tmp/seed4j/1234' }));
  });

  test('should change theme after toggle switch theme button', async ({ page }) => {
    const result = await interceptForever(page, '**/api/modules-landscape', fixture('landscape.json'));
    await page.emulateMedia({ colorScheme: 'dark' });
    await page.goto('/landscape');

    const loader = page.locator(dataSelector('landscape-loader'));
    await expect(loader).toBeVisible();
    result.send();
    await expect(loader).toHaveCount(0);

    const themeSwitchButton = page.locator(dataSelector('theme-switch-button'));
    await expect(themeSwitchButton).toBeAttached();
    await expect(themeSwitchButton).toBeHidden();
    await expect(themeSwitchButton).not.toBeChecked();
    await expect(page.locator('[aria-label="dark-theme"]')).toBeAttached();

    await page.locator('[aria-label="dark-theme"]').click();
    await expect(themeSwitchButton).toBeChecked();
    await expect(page.locator('[aria-label="light-theme"]')).toBeAttached();

    await page.locator('[aria-label="light-theme"]').click();
    await expect(themeSwitchButton).not.toBeChecked();
    await expect(page.locator('[aria-label="dark-theme"]')).toBeAttached();
  });

  test('should display landscape as default page', async ({ page }) => {
    await page.route('**/api/modules-landscape', route => route.fulfill(fixture('landscape.json')));
    await page.goto('/');

    await expect(page).toHaveURL(/\/landscape/);
  });

  test('should apply modules using WebServices', async ({ page }) => {
    const result = await interceptForever(page, '**/api/modules-landscape', fixture('landscape.json'));
    await page.goto('/landscape');

    const loader = page.locator(dataSelector('landscape-loader'));
    await expect(loader).toBeVisible();
    result.send();
    await expect(loader).toHaveCount(0);

    await page.route('**/api/apply-patches', route => route.fulfill({ status: 200 }));

    const modulesApplication = page.waitForRequest(
      request => request.method() === 'POST' && new URL(request.url()).pathname === '/api/apply-patches',
    );
    await page.locator(dataSelector('init-module')).click();
    await page.locator(dataSelector('parameter-packageName-field')).fill('value');
    await page.locator(dataSelector('modules-apply-all-button')).click();

    expect((await modulesApplication).postDataJSON()).toEqual({
      modules: ['init'],
      properties: {
        projectFolder: '/tmp/seed4j/1234',
        commit: true,
        parameters: {
          packageName: 'value',
        },
      },
    });
  });
});
