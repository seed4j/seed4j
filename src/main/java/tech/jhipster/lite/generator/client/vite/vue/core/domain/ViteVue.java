package tech.jhipster.lite.generator.client.vite.vue.core.domain;

import java.util.List;

public class ViteVue {

  private ViteVue() {}

  public static List<String> dependencies() {
    return List.of("vue");
  }

  public static List<String> devDependencies() {
    return List.of(
      "@rushstack/eslint-patch",
      "@types/jest",
      "@typescript-eslint/parser",
      "@vitejs/plugin-vue",
      "@vue/eslint-config-typescript",
      "@vue/test-utils",
      "eslint",
      "eslint-config-prettier",
      "eslint-plugin-vue",
      "jest",
      "jest-sonar-reporter",
      "jest-transform-stub",
      "ts-jest",
      "typescript",
      "vite",
      "vue-jest",
      "vue-tsc"
    );
  }
}
