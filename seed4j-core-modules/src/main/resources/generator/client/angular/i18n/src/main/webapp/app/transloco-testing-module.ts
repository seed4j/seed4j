import { TranslocoTestingModule, TranslocoTestingOptions } from '@jsverse/transloco';
import en from '../content/i18n/en.json';
import fr from '../content/i18n/fr.json';

export function getTranslocoModule(options: TranslocoTestingOptions = {}) {
  return TranslocoTestingModule.forRoot({
    langs: { en, fr },
    translocoConfig: {
      availableLangs: ['en', 'fr'],
      defaultLang: 'en',
    },
    preloadLangs: true,
    ...options,
  });
}
