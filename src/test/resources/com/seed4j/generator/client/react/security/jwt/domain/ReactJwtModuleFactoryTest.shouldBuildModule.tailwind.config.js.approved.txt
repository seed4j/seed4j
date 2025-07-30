const { heroui } = require('@heroui/react');

module.exports = {
  content: [
    './src/main/webapp/index.html',
    './src/main/webapp/**/*.{js,ts,jsx,tsx}',
    './node_modules/@heroui/theme/dist/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {},
  },
  darkMode: 'class',
  plugins: [heroui()],
};
