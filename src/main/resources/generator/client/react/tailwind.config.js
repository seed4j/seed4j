const { nextui } = require("@nextui-org/react");

module.exports = {
  content: [
    "./src/main/webapp/index.html",
    "./src/main/webapp/**/*.{js,ts,jsx,tsx}",
    './node_modules/@nextui-org/theme/dist/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {},
  },
  darkMode: "class",
  plugins: [
    nextui(),
  ],
}
