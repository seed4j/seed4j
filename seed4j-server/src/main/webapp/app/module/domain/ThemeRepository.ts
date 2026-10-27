import { ThemePreference as Theme } from '@/module/secondary/GetMediaPreference';

export interface ThemeRepository {
  get(): Theme;
  choose(theme: Theme): void;
}
