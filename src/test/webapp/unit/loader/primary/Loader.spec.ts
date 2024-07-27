import { Loader } from '@/shared/loader/infrastructure/primary/Loader';
import { describe, it, expect } from 'vitest';

describe('Loader', () => {
  it('should be loading for loading loader', () => {
    expect(Loader.loading().isLoading()).toBe(true);
  });

  it('should not get value for not loaded loader', () => {
    expect(() => Loader.loading().value()).toThrow();
  });

  it('should build loaded value', () => {
    const loader = Loader.loaded('pouet');

    expect(loader.isLoading()).toBe(false);
    expect(loader.value()).toBe('pouet');
  });

  it('should load value', () => {
    const loader = Loader.loading();

    loader.loaded('pouet');

    expect(loader.isLoading()).toBe(false);
    expect(loader.value()).toBe('pouet');
  });
});
