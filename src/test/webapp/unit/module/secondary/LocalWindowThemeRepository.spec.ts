import { describe, expect, it } from 'vitest';

import { LocalWindowThemeRepository } from '@/module/secondary/LocalWindowThemeRepository';

import { stubWindow } from '../primary/GlobalWindow.fixture';
import { fakeStorage } from './FakeStorage.fixture';

describe('LocalWindowThemeRepository', () => {
  it('should return light theme if no user theme preference stored in localStorage', () => {
    const storage = fakeStorage();
    storage.clear();

    const repo = new LocalWindowThemeRepository(window, storage);
    const defaultTheme = repo.get();

    expect(defaultTheme).toBe('light-theme');
  });

  it('should return theme which user prefers', () => {
    const storage = fakeStorage();
    storage.clear();

    const repo = new LocalWindowThemeRepository(window, storage);

    repo.choose('dark-theme');

    const newTheme1 = repo.get();

    expect(newTheme1).toBe('dark-theme');

    repo.choose('light-theme');

    const newTheme2 = repo.get();

    expect(newTheme2).toBe('light-theme');
  });

  it('should return theme which document matches', () => {
    const storage = fakeStorage();
    storage.clear();

    const windowStub1 = stubWindow('(prefers-color-scheme: dark)') as any;
    const repo1 = new LocalWindowThemeRepository(windowStub1, storage);

    const newTheme1 = repo1.get();

    expect(newTheme1).toBe('dark-theme');

    storage.clear();

    const windowStub2 = stubWindow() as any;
    const repo2 = new LocalWindowThemeRepository(windowStub2, storage);

    const newTheme2 = repo2.get();

    expect(newTheme2).toBe('light-theme');
  });
});
