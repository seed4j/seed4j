import type { Resource, ResourceKey, ResourceLanguage } from 'i18next';

export type Translation = Record<string, unknown>;
export type Translations = Record<string, Translation>;

const toLanguage = ([key, value]: [string, ResourceKey]): [string, ResourceLanguage] => [
  key,
  {
    translation: value,
  },
];

export const mergeTranslations = (translations: Translations[]): Translations =>
  translations
    .flatMap(translation => Object.entries(translation))
    .reduce(
      (acc, [key, translation]) => ({
        ...acc,
        [key]: acc[key] ? { ...acc[key], ...deepMerge(acc[key], translation) } : translation,
      }),
      {} as Translations,
    );

export const toTranslationResources = (...translations: Translations[]): Resource =>
  Object.entries(mergeTranslations(translations))
    .map(toLanguage)
    .reduce(
      (acc, current) => ({
        ...acc,
        [current[0]]: current[1],
      }),
      {},
    );

const deepMerge = (target: Translation, source: Translation): Translation => {
  for (const key of Object.keys(source)) {
    if (source[key] instanceof Object && key in target) {
      Object.assign(source[key], deepMerge(target[key] as Translation, source[key] as Translation));
    }
  }
  return { ...target, ...source };
};
