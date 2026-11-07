import { rm } from 'node:fs/promises';

import { COMPONENT_COVERAGE_FOLDER } from './Coverage';

export default async function clearCoverage(): Promise<void> {
  await rm(COMPONENT_COVERAGE_FOLDER, { recursive: true, force: true });
}
