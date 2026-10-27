export default {
  '*.{js,cjs,ts,tsx,vue}': ['oxlint --fix', 'prettier --write'],
  '*.pug': ['prettier --write'],
  '*.{css,scss}': ['stylelint --fix --allow-empty-input', 'prettier --write'],
  '*.{md,json,jsonc,yml,html,java,xml,feature,sh}': ['prettier --write'],
};
