import { pathsToModuleNameMapper } from 'ts-jest';
import { readFileSync } from 'fs';
import { resolve } from 'path';

const tsconfig = JSON.parse(readFileSync(resolve('./tsconfig.json'), 'utf-8'))

export default {
  transformIgnorePatterns: ['node_modules/(?!.*\\.mjs$|dayjs/esm|keycloak-js|@jsverse)'],
  transform: {
    '^.+\\.(ts|js|mjs|html|svg)$': [
      'jest-preset-angular',
      {
        tsconfig: '<rootDir>/tsconfig.spec.json',
      },
    ],
  },
  resolver: 'jest-preset-angular/build/resolvers/ng-jest-resolver.js',
  roots: ['<rootDir>', ...(tsconfig.compilerOptions.baseUrl ? [`<rootDir>/${tsconfig.compilerOptions.baseUrl}`] : [])],
  modulePaths: tsconfig.compilerOptions.baseUrl ? [`<rootDir>/${tsconfig.compilerOptions.baseUrl}`] : [],
  cacheDirectory: '<rootDir>/target/jest-cache',
  coverageDirectory: '<rootDir>/target/test-results/',
  coverageThreshold: {
    global: {
      statements: 100,
      branches: 100,
      functions: 100,
      lines: 100,
    },
  },
  moduleNameMapper: pathsToModuleNameMapper(tsconfig.compilerOptions.paths ?? {}, {
    prefix: `<rootDir>/${tsconfig.compilerOptions.baseUrl}/`,
  }),
  testEnvironmentOptions: {
    url: 'https://jestjs.io',
  },
  testMatch: ['<rootDir>/src/main/webapp/environments/**/@(*.)@(spec.ts)', '<rootDir>/src/main/webapp/app/**/@(*.)@(spec.ts)'],
  testResultsProcessor: 'jest-sonar-reporter',
};
