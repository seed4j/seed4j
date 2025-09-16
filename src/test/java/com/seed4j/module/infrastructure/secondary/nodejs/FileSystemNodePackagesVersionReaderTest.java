package com.seed4j.module.infrastructure.secondary.nodejs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.nodejs.NodePackageName;
import com.seed4j.module.domain.nodejs.NodePackageVersion;
import com.seed4j.module.domain.nodejs.NodePackagesVersionSource;
import com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FileSystemNodePackagesVersionReaderTest {

  private static final NodePackagesVersionSource COMMON = Seed4JNodePackagesVersionSource.COMMON.build();

  @Mock
  private ProjectFiles projectFiles;

  private FileSystemNodePackagesVersionReader reader;

  @BeforeEach
  void setup() {
    reader = new FileSystemNodePackagesVersionReader(projectFiles, List.of(Seed4JNodePackagesVersionSource.COMMON), "not-used");
  }

  @Test
  void shouldGetVersionFromDevSource() {
    mockProjectFiles();

    NodePackageVersion version = reader.get().get(new NodePackageName("@types/node"), COMMON);

    assertThat(version).isEqualTo(new NodePackageVersion("17.0.43"));
  }

  @Test
  void shouldGetVersionFromSource() {
    mockProjectFiles();

    NodePackageVersion version = reader.get().get(new NodePackageName("vue"), COMMON);

    assertThat(version).isEqualTo(new NodePackageVersion("1.2.3"));
  }

  @Test
  void shouldGetVersionFromEmptySourceWithEmptyDevSource() {
    emptyProjectFiles();

    NodePackageVersion version = reader.get().get(new NodePackageName("vue"), COMMON);

    assertThat(version).isEqualTo(new NodePackageVersion("1.2.3"));
  }

  private void mockProjectFiles() {
    when(projectFiles.readString(anyString())).thenReturn(
      """
      {
        "name": "seed4j-dependencies",
        "version": "0.0.0",
        "description": "Seed4J : used for Dependencies",
        "license": "Apache-2.0",
        "dependencies": {
          "vue": "1.2.3"
        },
        "devDependencies": {
          "@playwright/test": "1.22.2",
          "@prettier/plugin-xml": "2.2.0",
          "@types/jest": "28.1.1",
          "@types/node": "17.0.43",
          "@typescript-eslint/eslint-plugin": "5.28.0",
          "@typescript-eslint/parser": "5.28.0",
          "cypress": "10.1.0",
          "eslint": "8.17.0",
          "eslint-config-prettier": "9.1.0",
          "eslint-import-resolver-typescript": "2.7.1",
          "eslint-plugin-cypress": "2.12.1",
          "eslint-plugin-import": "2.26.0",
          "husky": "8.0.1",
          "jasmine-core": "4.2.0",
          "jest": "27.5.1",
          "lint-staged": "13.0.1",
          "prettier": "2.7.0",
          "prettier-plugin-java": "1.6.2",
          "prettier-plugin-packagejson": "2.2.18",
          "ts-jest": "27.1.4",
          "typescript": "4.7.3"
        }
      }
      """
    );
  }

  private void emptyProjectFiles() {
    when(projectFiles.readString(anyString())).thenReturn(
      """
      {
        "name": "seed4j-dependencies",
        "version": "0.0.0",
        "description": "Seed4J : used for Dependencies",
        "license": "Apache-2.0",
        "dependencies": {
          "vue": "1.2.3"
        },
      }
      """
    );
  }
}
