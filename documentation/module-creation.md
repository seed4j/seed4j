# Creating a Seed4J module

So, you want to create a Seed4J module? Great!

For that, you'll need to provide two main parts:

- `Seed4JModuleResource`: describe the module organization, it is used to generate the APIs;
- `Seed4JModule`: describe the changes done by the module.

You can start with the element you prefer but to create a `Seed4JModuleResource` you'll need to be able to build a `Seed4JModule`.

## Creating a Seed4J module

In fact, you don't just need to create one `Seed4JModule`, you'll need a factory able to create them since each instance depends on the properties chosen by the users.

A module lives in a single package of `seed4j-core-modules`, `com.seed4j.generator.my_module`. You can start with a simple test, in `seed4j-core-modules/src/test/java` (the test tools come from `seed4j-test`):

```java
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MyModuleFactoryTest {

  private static final MyModuleFactory factory = new MyModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModule(module).hasPrefixedFiles("src/main/java/com/seed4j/growth/my_package", "Dummy.java");
  }
}
```

A few things to note here:

- We are expecting to have a `buildModule(...)` method in `MyModuleFactory`;
- The `Seed4JModulesAssertions.assertThatModule(...)` will really apply the module to a project and give you a fluent API to ensure some operations;
- Even if the feedback loops are not perfect on that, they should be short enough to allow a decent TDD implementation of the factory (on eclipse with [infinitest](https://infinitest.github.io/) feedbacks are under a second).

So, now that we have a first test, we can do a simple implementation:

```java
import static com.seed4j.module.domain.Seed4JModule.*;

class MyModuleFactory {

  private static final Seed4JSource SOURCE = from("my-module");

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("Dummy.java"), toSrcMainJava().append(properties.packagePath()).append("my_package").append("Dummy.java"))
        .and()
      .build();
    // @formatter:on
  }
}
```

This implementation will take a file from `seed4j-core-modules/src/main/resources/generator/my-module` and put it in the generated project. The factory stays package-private: the module is exposed by its resource provider (`CoreModulesArchTest` checks it).

The file is a template named `Dummy.java.mustache` and can contain some mustache placeholders:

```java
package {{packageName}}.my_package;

public class Dummy {

  // ...
}
```

Those placeholders will be replaced by properties values during module application.

And this is it for this part of the documentation... Of course, you can do a lot more than that in the `Seed4JModule` but the goal of this documentation is not to go deep in this usage! You have a lot of running examples and you can always ask for help, we'll be really happy to help you provide your implementations!

## Add relevant dependencies required for the new module in the Version files

### Dependency resolution

- In the `seed4j-core-modules/src/main/resources/generator/dependencies` folder, different files are maintained to handle the dependencies for different tools/frameworks such as docker, maven, angular, etc.
- You can add the dependencies required for your new module in the respective files in the `dependencies` folder.
- The dependency versions are then automatically managed by the dependabot.

### Overview of Version files/folders in this dependencies folder

- **Docker versions**
  - You can add the docker images required for your module in the `seed4j-core-modules/src/main/resources/generator/dependencies/Dockerfile`
  - These dependencies are resolved using the [FileSystemDockerImagesReader](../seed4j-engine/src/main/java/com/seed4j/module/infrastructure/secondary/docker/FileSystemDockerImagesReader.java), an implementation of the `DockerImagesReader` bean to read from a local file.

- **Java versions**
  - You can add the java dependencies required for your module in the `seed4j-core-modules/src/main/resources/generator/dependencies/pom.xml`
  - These dependencies are resolved using [FileSystemMavenDependenciesReader](../seed4j-engine/src/main/java/com/seed4j/module/infrastructure/secondary/javadependency/FileSystemMavenDependenciesReader.java), an implementation of the `JavaDependenciesReader` bean to read from a local file.

- **NPM versions**
  - Common npm dependencies can be added in the `seed4j-core-modules/src/main/resources/generator/dependencies/common/package.json`
  - Framework specific npm dependencies can be added in the `package.json` of the respective framework folders. For e.g.: `seed4j-core-modules/src/main/resources/generator/dependencies/react/package.json`
  - These dependencies are resolved using [FileSystemNodePackagesVersionReader](../seed4j-engine/src/main/java/com/seed4j/module/infrastructure/secondary/nodejs/FileSystemNodePackagesVersionReader.java), an implementation of the `NodePackagesVersionsReader` bean to read from a local file.

## Creating Seed4JModuleResource

The `Seed4JModuleResource` makes the module available: the engine applies it, and the server exposes it in its API.

Let's start by creating a gherkin scenario for that. So in `seed4j-core-modules/src/test/features/my-module.feature` we'll do:

```
Feature: My module

  Scenario: Should apply my module
    When I apply "my-module" module to default project
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/my_package"
      | Dummy.java |
```

> The goal of this test is not to duplicate your factory unit test! Just ensure that one change done by your module is visible here, it is enough since we only want to ensure that the module is found and applied by the engine.

You can now run `CucumberTest` and ensure that it is failing as expected (with an unknown slug).

First, add the slug of your module to the `Seed4JCoreModuleSlug` enum. Then, in the same package as the factory, create a resource provider: the engine discovers it with `ServiceLoader`, there is no Spring in the modules.

```java
@AutoService(Seed4JModuleResourceProvider.class)
public class MyModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.MY_MODULE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Group", "This is my module")
      .standalone()
      .tags("server")
      .factory(new MyModuleFactory()::buildModule);
  }
}
```

In fact, you don't really have choices here, the `Seed4JModuleResource.builder()` is fluent and will only let you go to the next possible step.
The most confusing one may be the last one `.factory(new MyModuleFactory()::buildModule)` which is, in fact, a method called to build the module. If your factory needs the engine services (docker images, node versions...), give it the `services` received by `get`.

In your `Seed4JModuleResource` you can define additional properties and an organization to display your module in the landscape (replacing `.standalone()`). Here again, you have a lot of examples to rely on.

## Applying module in CI

Now that you are confident about your module's action you can add it to the Seed4J Continuous Integration by adding it in the `fullapp` application in [generate.sh](../tests-ci/generate.sh), so it will be compiled and analyzed by SonarQube. You can also create a brand new app if needed.

Note: `tests-ci/generate.sh` accepts optional parameters to customize the generated project. Full syntax:

```
./tests-ci/generate.sh <application> [<java-build-tool> <spring-configuration-format> <node-package-manager>]
```

Defaults when the optional parameters are omitted are: `java-build-tool` = `maven`, `spring-configuration-format` = `yaml`, `node-package-manager` = `npm`.

Finally, append your app name in [github-actions.yml](../.github/workflows/github-actions.yml), in `generation` pipeline.
