import js from '@eslint/js';
import prettier from 'eslint-config-prettier/flat';
import globals from 'globals';
import typescript from 'typescript-eslint';
import react from 'eslint-plugin-react/configs/recommended.js';

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
  {
    files: ['src/main/webapp/**/*.{ts,tsx}', 'src/test/webapp/unit/**/*.{ts,tsx}'],
    extends: [...typescript.configs.recommendedTypeChecked, react],
    settings: {
      react: {
        version: 'detect',
      },
    },
    languageOptions: {
      globals: { ...globals.browser },
      parserOptions: {
        project: ['./tsconfig.json'],
      },
    },
    rules: {
      'react/react-in-jsx-scope': 'off',
      '@typescript-eslint/no-explicit-any': 'off',
      '@typescript-eslint/await-thenable': 'off',
      '@typescript-eslint/consistent-type-imports': 'error',
      '@typescript-eslint/no-misused-promises': [
        'error',
        {
          checksVoidReturn: false,
        },
      ],
    },
  },
  prettier,
);
