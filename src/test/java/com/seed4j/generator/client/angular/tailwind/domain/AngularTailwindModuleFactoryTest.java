package com.seed4j.generator.client.angular.tailwind.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions;
import org.junit.jupiter.api.Test;

@UnitTest
class AngularTailwindModuleFactoryTest {

  private static final AngularTailwindModuleFactory factory = new AngularTailwindModuleFactory();

  @Test
  void shouldBuildTailwindModule() {
    Seed4JModule module = factory.buildModule(
      Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("seed4j").build()
    );

    Seed4JModulesAssertions.Seed4JModuleAsserter asserter = assertThatModuleWithFiles(
      module,
      packageJsonFile(),
      stylesFile(),
      postcssrcFile(),
      appHtmlFile()
    );
    asserter
      .hasFile("package.json")
      .containing(nodeDependency("tailwindcss"))
      .containing(nodeDependency("@tailwindcss/postcss"))
      .containing(nodeDependency("postcss"))
      .and()
      .hasFile(".postcssrc.json")
      .and()
      .hasFile("src/main/webapp/styles.css")
      .containing("@import \"tailwindcss\";")
      .and()
      .hasFile("src/main/webapp/app/app.html")
      .containing("<h2 class=\"text-2xl font-bold text-blue-600\">Tailwind CSS Enabled</h2>");
  }

  private ModuleFile stylesFile() {
    return file("src/test/resources/projects/angular/styles.css", "src/main/webapp/styles.css");
  }

  private ModuleFile postcssrcFile() {
    return file("src/test/resources/projects/angular/.postcssrc.json", ".postcssrc.json");
  }

  private ModuleFile appHtmlFile() {
    return file("src/test/resources/projects/angular/app.html", "src/main/webapp/app/app.html");
  }
}
