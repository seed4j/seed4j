import { test as base } from '@playwright/test';
import { randomUUID } from 'node:crypto';
import { mkdir, writeFile } from 'node:fs/promises';
import path from 'node:path';

export const COMPONENT_COVERAGE_FOLDER = path.resolve(import.meta.dirname, '../../../../../target/frontend-coverage/components-tests/raw');

type FileCoverage = { path: string; inputSourceMap?: { sources: string[]; sourceRoot?: string } };
type Coverage = Record<string, FileCoverage>;
type CoverageWindow = Window & { __coverage__?: Coverage };

// The source maps of the Vue SFC scripts reference the file with a query (`X.component.ts?vue&type=script…`): nyc would
// report these virtual files instead of the real ones. Point the sources back to the file, as @cypress/code-coverage does.
const fixSourcePaths = (coverage: Coverage): Coverage => {
  Object.values(coverage).forEach(file => {
    if (!file.inputSourceMap) {
      return;
    }

    const fileName = path.basename(file.path);
    file.inputSourceMap.sourceRoot = '';
    file.inputSourceMap.sources = file.inputSourceMap.sources.map(source => (source.includes(fileName) ? file.path : source));
  });

  return coverage;
};

/**
 * Playwright test saving, after each test, the Istanbul coverage collected in the page by vite-plugin-istanbul.
 */
export const test = base.extend<{ coverage: void }>({
  coverage: [
    async ({ page }, use) => {
      await use();

      const coverage = await page.evaluate(() => (window as CoverageWindow).__coverage__);
      if (coverage) {
        await mkdir(COMPONENT_COVERAGE_FOLDER, { recursive: true });
        await writeFile(path.join(COMPONENT_COVERAGE_FOLDER, `${randomUUID()}.json`), JSON.stringify(fixSourcePaths(coverage)));
      }
    },
    { auto: true },
  ],
});

export { expect } from '@playwright/test';
