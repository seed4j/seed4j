# Creating a Seed4J module

So, you want to create a Seed4J module? Great!

For that, you'll need to provide two main parts:

- `SeedModuleResource`: describe the module organization, it is used to generate the APIs;
- `SeedModule`: describe the changes done by the module.

You can start with the element you prefer but to create a `SeedModuleResource` you'll need to be able to build a `SeedModule`.

## Creating a Seed4J module

In fact, you don't just need to create one `SeedModule`, you'll need a factory able to create them since each instance depends on the properties chosen by the users.

So, as this is the business of Seed4J you probably want to create a `com.seed4j.generator.my_module.domain` package. And you can start with a simple test:

```java
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MyModuleFactoryTest {

  private static final MyModuleFactory factory = new MyModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module).hasPrefixedFiles("src/main/java/com/seed4j/growth/my_package", "Dummy.java");
  }
}
```

A few things to note here:

- We are expecting to have a `buildModule(...)` method in `MyModuleFactory`;
- The `SeedModulesAssertions.assertThatModule(...)` will really apply the module to a project and give you a fluent API to ensure some operations;
- Even if the feedback loops are not perfect on that, they should be short enough to allow a decent TDD implementation of the factory (on eclipse with [infinitest](https://infinitest.github.io/) feedbacks are under a second).

So, now that we have a first test, we can do a simple implementation:

```java
import static com.seed4j.module.domain.SeedModule.*;

public class MyModuleFactory {

  private static final SeedSource SOURCE = from("my-module");

  public SeedModule buildModule(SeedModuleProperties properties) {
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

This implementation will take a file from `src/main/resources/generator/my-module` and put it in the generated project.

The file is a template named `Dummy.java.mustache` and can contain some mustache placeholders:

```java
package {{packageName}}.my_package;

public class Dummy {

  // ...
}
```

Those placeholders will be replaced by properties values during module application.

And this is it for this part of the documentation... Of course, you can do a lot more than that in the `SeedModule` but the goal of this documentation is not to go deep in this usage! You have a lot of running examples and you can always ask for help, we'll be really happy to help you provide your implementations!

## Add relevant dependencies required for the new module in the Version files

### Dependency resolution

- In the `src/main/resources/generator/dependencies` folder, different files are maintained to handle the dependencies for different tools/frameworks such as docker, maven, angular, etc.
- You can add the dependencies required for your new module in the respective files in the `dependencies` folder.
- The dependency versions are then automatically managed by the dependabot.

### Overview of Version files/folders in this dependencies folder

- **Docker versions**
  - You can add the docker images required for your module in the `src/main/resources/generator/dependencies/Dockerfile`
  - These dependencies are resolved using the [FileSystemDockerImagesReader](https://github.com/seed4j/seed4j/blob/main/src/main/java/com/seed4j/module/infrastructure/secondary/docker/FileSystemDockerImagesReader.java), an implementation of the `DockerImagesReader` bean to read from a local file.

- **Java versions**
  - You can add the java dependencies required for your module in the `src/main/resources/generator/dependencies/pom.xml`
  - These dependencies are resolved using [FileSystemMavenDependenciesReader](https://github.com/seed4j/seed4j/blob/main/src/main/java/com/seed4j/module/infrastructure/secondary/javadependency/FileSystemMavenDependenciesReader.java), an implementation of the `JavaDependenciesReader` bean to read from a local file.

- **NPM versions**
  - Common npm dependencies can be added in the `src/main/resources/generator/dependencies/common/package.json`
  - Framework specific npm dependencies can be added in the `package.json` of the respective framework folders. For e.g.: `src/main/resources/generator/dependencies/react/package.json`
  - These dependencies are resolved using [FileSystemNpmVersionReader](https://github.com/seed4j/seed4j/blob/main/src/main/java/com/seed4j/module/infrastructure/secondary/npm/FileSystemNpmVersionReader.java), an implementation of the `NpmVersionsReader` bean to read from a local file.

## Creating SeedModuleResource

As the main goal of a `SeedModuleResource` is to expose a WebService.

Let's start by creating a gherkin scenario for that. So in `src/test/features/my-module.feature` we'll do:

```
Feature: My module

  Scenario: Should apply my module
    When I apply "my-module" module to default project
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/my_package"
      | Dummy.java |
```

> The goal of this test is not to duplicate your factory unit test! Just ensure that one change done by your module is visible here, it is enough since we only want to ensure that the WebService is working as expected.

You can now run `CucumberTest` and ensure that it is failing as expected (with a 404).

To be used by Seed4J, the `SeedModuleResource` needs to be a Spring bean so, let's create a configuration in `com.seed4j.generator.my_module.infrastructure.primary`:

```java
@Configuration
class MyModuleModuleConfiguration {

  @Bean
  SeedModuleResource myModule(MyModuleApplicationService myModules) {
    return SeedModuleResource.builder()
      .slug(Seed4JModuleSlug.MY_MODULE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Group", "This is my module")
      .standalone()
      .tags("server")
      .factory(myModules::buildModule);
  }
}
```

In fact, you don't really have choices here, the `SeedModuleResource.builder()` is fluent and will only let you go to the next possible step.
The most confusing one may be the last one `.factory(myModules::buildModule)` which is, in fact, a method called to build the module.

For this to work, we'll need to add a simple orchestration class in `com.seed4j.generator.my_module.application`:

```java
@Service
public class MyModuleApplicationService {

  private final MyModuleFactory factory;

  public MyModuleApplicationService() {
    factory = new MyModuleFactory();
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
```

In your `SeedModuleResource` you can define additional properties and an organization to display your module in the landscape (replacing `.standalone()`). Here again, you have a lot of examples to rely on.

## Applying module in CI

Now that you are confident about your module's action you can add it to the Seed4J Continuous Integration by adding it in the `fullapp` application in [generate.sh](../tests-ci/generate.sh), so it will be compiled and analyzed by SonarQube. You can also create a brand new app if needed.

If your app launches a docker container, you must edit [start_docker_compose.sh](../tests-ci/start_docker_compose.sh).

Finally, append your app name in [github-actions.yml](../.github/workflows/github-actions.yml), in `generation` pipeline.
