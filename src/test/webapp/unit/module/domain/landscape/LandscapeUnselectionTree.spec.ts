import { describe, expect, it } from 'vitest';

import { LandscapeUnselectionTree } from '@/module/domain/landscape/LandscapeUnselectionTree';

import { moduleSlug } from '../Modules.fixture';

describe('Unselection tree', () => {
  it('should not include unknown element', () => {
    expect(new LandscapeUnselectionTree([]).includes(moduleSlug('unknown'))).toBe(false);
  });

  it('should include known module', () => {
    expect(new LandscapeUnselectionTree([moduleSlug('init')]).includes(moduleSlug('init'))).toBe(true);
  });

  it('should include known feature', () => {
    expect(new LandscapeUnselectionTree([moduleSlug('jpa')]).includes(moduleSlug('jpa'))).toBe(true);
  });
});
