package com.seed4j.generator.client.angular.tailwind.domain;

import static com.seed4j.module.domain.Seed4JModule.*;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.ANGULAR;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class AngularTailwindModuleFactory {

  private static final Seed4JSource TAILWIND_SOURCE = from("client/angular/tailwind");
  private static final Seed4JDestination INDEX_DEST = to(".");
  private static final Seed4JDestination INDEX_CLIENT = to("src/main/webapp/");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDevDependency(packageName("tailwindcss"), ANGULAR)
        .addDevDependency(packageName("@tailwindcss/postcss"), ANGULAR)
        .addDevDependency(packageName("postcss"), ANGULAR)
        .and()
      .files()
        .batch(TAILWIND_SOURCE, INDEX_DEST)
          .addFile(".postcssrc.json")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path(INDEX_CLIENT.append("styles.css").get()))
          .add(fileStart(), "@import \"tailwindcss\";\n\n")
          .and()
        .and()
      .optionalReplacements()
        .in(path(INDEX_CLIENT.append("app/app.html").get()))
          .add(lineAfterRegex("Angular \\+ TypeScript"), properties.indentation().times(2) + "<h2 class=\"text-2xl font-bold text-blue-600\">Tailwind CSS Enabled</h2>")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
