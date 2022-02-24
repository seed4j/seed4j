package tech.jhipster.lite.generator.client.vite.react.core.domain;

import java.util.List;
import java.util.Map;

public class ViteReact {

  private ViteReact() {}

  public static List<String> dependencies() {
    return List.of("react", "react-dom");
  }

  public static List<String> devDependencies() {
    return List.of(
      "@testing-library/jest-dom",
      "@testing-library/react",
      "@testing-library/user-event",
      "@types/jest",
      "@types/node",
      "@types/react",
      "@types/react-dom",
      "@vitejs/plugin-react",
      "jest",
      "jest-sonar-reporter",
      "jest-css-modules",
      "typescript",
      "ts-jest",
      "ts-node",
      "vite"
    );
  }

  public static Map<String, String> scripts() {
    return Map.of(
      "dev",
      "vite",
      "start",
      "vite",
      "build",
      "tsc && vite build --emptyOutDir",
      "preview",
      "vite preview",
      "test",
      "jest",
      "test:watch",
      "jest --watch"
    );
  }

  public static List<String> files() {
    return List.of("tsconfig.json", "vite.config.ts", "jest.config.ts");
  }

  public static Map<String, String> reactFiles() {
    String pathApp = "src/main/webapp/app";
    String pathWebapp = "src/main/webapp";
    String pathPrimaryApp = "src/main/webapp/app/common/primary/app";
    String pathTestPrimaryApp = "src/test/javascript/spec/common/primary/app";
    String pathConfig = "src/main/webapp/config";

    return Map.ofEntries(
      Map.entry("index.html", pathWebapp),
      Map.entry("index.css", pathApp),
      Map.entry("index.tsx", pathApp),
      Map.entry("vite-env.d.ts", pathApp),
      Map.entry("App.css", pathPrimaryApp),
      Map.entry("App.tsx", pathPrimaryApp),
      Map.entry("App.test.tsx", pathTestPrimaryApp),
      Map.entry("setupTests.ts", pathConfig)
    );
  }
}
