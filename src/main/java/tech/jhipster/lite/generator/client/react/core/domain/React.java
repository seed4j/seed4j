package tech.jhipster.lite.generator.client.react.core.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class React {

  private React() {}

  public static List<String> dependencies() {
    return List.of(
      "@testing-library/jest-dom",
      "@testing-library/react",
      "@testing-library/user-event",
      "@types/jest",
      "@types/node",
      "@types/react",
      "@types/react-dom",
      "react",
      "react-dom",
      "react-scripts",
      "typescript"
    );
  }

  public static Map<String, String> scripts() {
    // @formatter:off
    return Map.of(
      "start", "react-scripts start",
      "build", "react-scripts build",
      "test", "react-scripts test"
    );
    // @formatter:on
  }

  public static List<String> files() {
    // @formatter:off
    return List.of(
      "tsconfig.json"
    );
    // @formatter:on
  }

  public static Map<String, String> reactFiles() {
    // @formatter:offProjectLocalRepository
    return Map.ofEntries(
      Map.entry("index.html", "public"),
      Map.entry("App.css", "src/main/webapp"),
      Map.entry("App.test.tsx", "src/main/webapp"),
      Map.entry("App.tsx", "src/main/webapp"),
      Map.entry("index.css", "src/main/webapp"),
      Map.entry("index.tsx", "src"),
      Map.entry("react-app-env.d.ts", "src/main/webapp"),
      Map.entry("setupTests.ts", "src")
    );
    // @formatter:on
  }
}
