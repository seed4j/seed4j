package tech.jhipster.lite.generator.client.react.core.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class React {

  private React() {}

  public static List<String> dependencies() {
    return List.of("react", "react-dom", "react-scripts");
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
      "react-scripts",
      "typescript"
    );
  }

  public static Map<String, String> scripts() {
    return Map.of("start", "react-scripts start", "build", "react-scripts build", "test", "react-scripts test");
  }

  public static List<String> files() {
    return List.of("tsconfig.json");
  }

  public static Map<String, String> reactFiles() {
    return Map.ofEntries(
      Map.entry("index.html", "public"),
      Map.entry("index.css", "src/main/webapp"),
      Map.entry("index.tsx", "src"),
      Map.entry("react-app-env.d.ts", "src/main/webapp"),
      Map.entry("App.css", "src/main/webapp/app/common/primary/app"),
      Map.entry("App.tsx", "src/main/webapp/app/common/primary/app"),
      Map.entry("App.test.tsx", "src/test/javascript/spec/common/primary/app"),
      Map.entry("setupTests.ts", "src")
    );
  }
}
