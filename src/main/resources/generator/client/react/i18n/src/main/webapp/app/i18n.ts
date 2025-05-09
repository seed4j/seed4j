import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import Backend from 'i18next-http-backend';
import { initReactI18next } from 'react-i18next';

i18n
  .use(Backend)
  .use(LanguageDetector)
  .use(initReactI18next)

  .init({
    fallbackLng: 'en',
    debug: false,

    interpolation: {
      escapeValue: false,
    },
    backend: {
      loadPath: '../assets/locales/{{ lng }}/{{ ns }}.json',
    },
  })
  .catch(error => {
    console.error('i18n initialization failed', error);
  });

export default i18n;
