import { Loader, LoadingState } from '@/shared/loader/infrastructure/primary/Loader';
import { describe, expect, it } from 'vitest';

describe('Loader', () => {
  describe('Loading loader', () => {
    const loader = Loader.loading<string>();

    it('should be in a loading state', () => {
      expect(loader.state).toBe(LoadingState.LOADING);
      expect(loader.isLoading).toBe(true);
      expect(loader.isLoaded).toBe(false);
      expect(loader.isInError).toBe(false);
    });

    it('should not get value', () => {
      expect(() => loader.value).toThrowError();
    });

    it('should not get error', () => {
      expect(() => loader.error).toThrowError();
    });
  });

  describe('Loaded loader', () => {
    const loader = Loader.loaded('value');

    it('should be loaded', () => {
      expect(loader.state).toBe(LoadingState.LOADED);
      expect(loader.isLoading).toBe(false);
      expect(loader.isLoaded).toBe(true);
      expect(loader.isInError).toBe(false);
      expect(loader.value).toBe('value');
    });

    it('should not get error', () => {
      expect(() => loader.error).toThrowError();
    });
  });

  describe('Error loader', () => {
    const loader = Loader.error(new Error('oops'));

    it('should be in error state', () => {
      expect(loader.state).toBe(LoadingState.ERROR);
      expect(loader.isLoading).toBe(false);
      expect(loader.isLoaded).toBe(false);
      expect(loader.isInError).toBe(true);
      expect(loader.error.message).toBe('oops');
    });

    it('should not get value', () => {
      expect(() => loader.value).toThrowError();
    });
  });
});
