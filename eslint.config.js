import js from '@eslint/js';
import cypress from 'eslint-plugin-cypress/flat';
import prettier from 'eslint-plugin-prettier/recommended';
import vue from 'eslint-plugin-vue';
import globals from 'globals';
import typescript from 'typescript-eslint';

export default typescript.config(
  {
    languageOptions: {
      globals: globals.node,
    },
  },
  {
    ignores: ['target/', 'src/test/resources/'],
  },
  js.configs.recommended,
  ...typescript.configs.recommended.map(config => (config.name === 'typescript-eslint/base' ? config : { ...config, files: ['**/*.ts'] })),
  ...vue.configs['flat/recommended'],
  {
    files: ['**/*.vue'],
    languageOptions: {
      parserOptions: { parser: '@typescript-eslint/parser' },
    },
  },
  {
    files: ['src/*/webapp/**/*.vue', 'src/*/webapp/**/*.ts'],
    rules: {
      quotes: ['error', 'single', { avoidEscape: true }],
      '@typescript-eslint/no-explicit-any': 'off',
      'vue/multi-word-component-names': 'off',
      'vue/no-reserved-component-names': 'off',
      '@typescript-eslint/no-empty-object-type': 'off',
    },
  },
  {
    files: ['src/test/webapp/component/integration/**/*.ts'],
    extends: [...typescript.configs.recommendedTypeChecked, cypress.configs.recommended],
    languageOptions: {
      parserOptions: {
        project: ['src/test/webapp/component/tsconfig.json'],
      },
    },
    rules: {
      '@typescript-eslint/no-unsafe-assignment': 'off',
    },
  },
  {
    files: ['src/test/webapp/e2e/integration/**/*.ts'],
    extends: [...typescript.configs.recommendedTypeChecked, cypress.configs.recommended],
    languageOptions: {
      parserOptions: {
        project: ['src/test/webapp/e2e/tsconfig.json'],
      },
    },
    rules: {
      '@typescript-eslint/no-unsafe-assignment': 'off',
    },
  },
  prettier,
);
