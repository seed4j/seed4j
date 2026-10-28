import i18n from '@/i18n';
import { mergeTranslations, type Translations } from '@/Translations';

describe('i18n configuration', () => {
  it('should load en translation', () => {
    expect((i18n.getResourceBundle('en', '') as Translations).home.translationEnabled).toBe('Internationalization enabled');
  });

  it('should load fr translation', () => {
    expect((i18n.getResourceBundle('fr', '') as Translations).home.translationEnabled).toBe('Internationalisation activée');
  });

  describe('mergeTranslations function', () => {
    it('should merge translations correctly when keys overlap', () => {
      const translationSet1 = {
        en: {
          home: {
            translationEnabled: 'Internationalization enabled',
            welcome: 'Welcome',
          },
        },
      };

      const translationSet2 = {
        en: {
          home: {
            anotherMessage: 'Another message',
          },
        },
      };

      const mergedResult = mergeTranslations([translationSet1, translationSet2]);

      expect(mergedResult).toEqual({
        en: {
          home: {
            translationEnabled: 'Internationalization enabled',
            welcome: 'Welcome',
            anotherMessage: 'Another message',
          },
        },
      });
    });
  });
});
