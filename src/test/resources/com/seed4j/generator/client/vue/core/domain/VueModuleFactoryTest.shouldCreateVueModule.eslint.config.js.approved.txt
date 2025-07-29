import js from '@eslint/js';
import vue from 'eslint-plugin-vue';
import prettier from 'eslint-config-prettier/flat';
import globals from 'globals';
import typescript from 'typescript-eslint';

export default typescript.config(
  {
    languageOptions: {
      globals: {
        ...globals.node,
      },
    },
  },
  {
    ignores: ['target/'],
  },
  js.configs.recommended,
  ...typescript.configs.recommended.map(config => (config.name === 'typescript-eslint/base' ? config : { ...config, files: ['**/*.ts'] })),
  ...vue.configs['flat/recommended'],
  {
    files: ['**/*.vue'],
    languageOptions: {
      parserOptions: { parser: '@typescript-eslint/parser' },
      globals: { ...globals.browser },
    },
  },
  {
    files: ['src/*/webapp/**/*.vue', 'src/*/webapp/**/*.ts'],
    languageOptions: {
      globals: { ...globals.browser },
    },
    rules: {
      '@typescript-eslint/consistent-type-imports': 'error',
      '@typescript-eslint/no-empty-object-type': 'off',
      '@typescript-eslint/no-explicit-any': 'off',
      'vue/html-self-closing': 'off',
      quotes: ['error', 'single', { avoidEscape: true }],
    },
  },
  prettier,
);
