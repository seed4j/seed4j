import { expect, test } from '../support/Coverage';
import { fixture, interceptForever } from '../support/Interceptor';
import { dataSelector } from '../support/Selector';

test.describe('Patch', () => {
  test.beforeEach(async ({ page }) => {
    await page.route('**/api/project-folders', route => route.fulfill({ body: '/tmp/seed4j/1234' }));
  });

  test('should display loader while loading modules', async ({ page }) => {
    const result = await interceptForever(page, '**/api/modules', fixture('modules.json'));
    await page.goto('/patches');

    const loader = page.locator(dataSelector('modules-loader'));
    await expect(loader).toBeVisible();
    result.send();

    await expect(loader).toHaveCount(0);
    await expect(page.locator(dataSelector('modules-list'))).toBeVisible();
    await expect(page.locator(dataSelector('module-spring-test-application-button'))).toBeEnabled();
    await expect(page.locator(dataSelector('module-spring-cucumber-application-button'))).toBeEnabled();
    await expect(page.locator(dataSelector('folder-path-field'))).toHaveValue('/tmp/seed4j/1234');
  });

  test('should change theme after toggle switch theme button', async ({ page }) => {
    const result = await interceptForever(page, '**/api/modules', fixture('modules.json'));
    await page.emulateMedia({ colorScheme: 'dark' });
    await page.goto('/patches');

    const loader = page.locator(dataSelector('modules-loader'));
    await expect(loader).toBeVisible();
    result.send();
    await expect(loader).toHaveCount(0);
    await expect(page.locator(dataSelector('modules-list'))).toBeVisible();

    const themeSwitchButton = page.locator(dataSelector('theme-switch-button'));
    await expect(themeSwitchButton).not.toBeChecked();
    await expect(page.locator('[aria-label="dark-theme"]')).toBeAttached();

    await page.locator('[aria-label="dark-theme"]').click();
    await expect(themeSwitchButton).toBeChecked();
    await expect(page.locator('[aria-label="light-theme"]')).toBeAttached();

    await page.locator('[aria-label="light-theme"]').click();
    await expect(themeSwitchButton).not.toBeChecked();
    await expect(page.locator('[aria-label="dark-theme"]')).toBeAttached();
  });

  test('should apply module without properties', async ({ page }) => {
    await page.route('**/api/modules', route => route.fulfill(fixture('modules.json')));
    await page.goto('/patches');

    await page.route('**/api/modules/spring-test/apply-patch', route => route.fulfill({ status: 200 }));

    const springTestCreation = page.waitForRequest(
      request => request.method() === 'POST' && new URL(request.url()).pathname === '/api/modules/spring-test/apply-patch',
    );
    await page.locator(dataSelector('folder-path-field')).fill('test');
    await page.locator(dataSelector('module-spring-test-application-button')).click();

    expect((await springTestCreation).postDataJSON()).toEqual({
      projectFolder: 'test',
      commit: true,
      parameters: {},
    });
  });

  test('should apply module with properties', async ({ page }) => {
    await page.route('**/api/modules', route => route.fulfill(fixture('modules.json')));
    await page.goto('/patches');

    await page.route('**/api/modules/spring-cucumber/apply-patch', route => route.fulfill({ status: 200 }));

    const springCucumberCreation = page.waitForRequest(
      request => request.method() === 'POST' && new URL(request.url()).pathname === '/api/modules/spring-cucumber/apply-patch',
    );
    await page.locator(dataSelector('spring-cucumber-module-content')).click();
    await page.locator(dataSelector('folder-path-field')).fill('test');
    await page.locator(dataSelector('parameter-baseName-field')).fill('seed4j');
    await page.locator(dataSelector('parameter-optionalBoolean-field')).selectOption('true');
    await page.locator(dataSelector('parameter-optionalInteger-field')).fill('42');
    await page.locator(dataSelector('module-spring-cucumber-application-button')).click();

    expect((await springCucumberCreation).postDataJSON()).toEqual({
      projectFolder: 'test',
      commit: true,
      parameters: {
        baseName: 'seed4j',
        optionalBoolean: true,
        optionalInteger: 42,
      },
    });
  });
});
