import { describe, expect, it } from 'vitest';

import { Loader } from '@/shared/loader/infrastructure/primary/Loader';

describe('Loader', () => {
  it('should be loading for loading loader', () => {
    expect(Loader.loading().isLoading()).toBe(true);
  });

  it('should not get value for not loaded loader', () => {
    expect(() => Loader.loading().value()).toThrow();
  });

  it('should build loaded value', () => {
    const loader = Loader.loaded('hello');

    expect(loader.isLoading()).toBe(false);
    expect(loader.value()).toBe('hello');
  });

  it('should load value', () => {
    const loader = Loader.loading();

    loader.loaded('hello');

    expect(loader.isLoading()).toBe(false);
    expect(loader.value()).toBe('hello');
  });
});
