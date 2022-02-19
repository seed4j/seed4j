export default {
  transform: {
    '^.+\\.tsx?$': 'ts-jest',
  },
  moduleNameMapper: {
    '\\.(css|less|scss|sss|styl)$': '<rootDir>/node_modules/jest-css-modules',
  },
  testEnvironment: 'jsdom',
  collectCoverage: true,
  collectCoverageFrom: ['src/main/webapp/**/*.{js,ts,tsx}', '!**/*.d.ts', '!src/main/webapp/config/**/*', '!src/main/webapp/app/index.tsx'],
  coverageReporters: ['html', 'json-summary', 'text-summary', 'lcov', 'clover'],
  coverageDirectory: '<rootDir>/target/test-results/',
  coverageThreshold: {
    global: {
      statements: 100,
      branches: 100,
      functions: 100,
      lines: 100,
    },
  },
  modulePathIgnorePatterns: ['<rootDir>/src/main/resources/'],
  testResultsProcessor: 'jest-sonar-reporter',
};
