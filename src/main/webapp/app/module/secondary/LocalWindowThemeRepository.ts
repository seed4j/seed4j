import { ThemeRepository } from '../domain/ThemeRepository';
import { getMediaPreference, ThemePreference as Theme } from './GetMediaPreference';

const THEME_STORAGE_KEY = 'theme';

export class LocalWindowThemeRepository implements ThemeRepository {
  constructor(
    private readonly win: Window,
    private readonly storage: Storage,
  ) {}

  get(): Theme {
    return (this.storage.getItem(THEME_STORAGE_KEY) as Theme) || getMediaPreference(this.win);
  }

  choose(theme: Theme): void {
    this.storage.setItem(THEME_STORAGE_KEY, theme);
    this.win.document.documentElement.className = theme;
  }
}
