# Creating a JHLite module

So you want to create a JHLite module? Great!  

For that you'll need to provide 2 main parts:

* `JHipsterModuleResource`: describe the module organization, it is used to generate the APIs;
* `JHipsterModule`: describe the changes done by the module.

You can start by the element you prefer but to create a `JHipsterModuleResource` you'll need to be able to build a `JHipsterModule`.

## Creating a JHipsterModule

In fact, you don't just need to create one `JHipsterModule`, you'll need a factory able to create them since each instance depends on the properties chosen by the users.

So, as this is the business of JHLite you probably want to create a `tech.jhipster.lite.generator.my_module.domain` package. And you can start with a simple test: 

```java
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class MyModuleFactoryTest {

  private static final MyModuleFactory factory = new MyModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    //@formatter:off
    assertThatModule(module)
      .hasJavaSources("com/jhipster/test/my_package/Dummy.java");
    //@formatter:on
  }
}
```

A few things to note here: 

* We are expecting to have a `buildModule(...)` method in `MyModuleFactory`;
* The `JHipsterModulesAssertions.assertThatModule(...)` will really apply the module to a project and give you a fluent API to ensure some operations;
* Even if the feedback loops are not perfect on that they should be short enough to allow a decent TDD implementation of the factory (on eclipse with [infinitest](https://infinitest.github.io/) feedbacks are under a second).

So, now that we have a first test we can do a simple implementation: 

```java
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

public class MyModuleFactory {

  private static final JHipsterSource SOURCE = from("my-module");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("Dummy.java"), toSrcMainJava().append(properties.packagePath()).append("my_package").append("Dummy.java"))
        .and()
      .build();
    //@formatter:on
  }
}
```

This implementation will take a file from `src/main/resources/generator/my-module` and put it in the generated project.

The file is a template named `Dummy.java.mustache` and can contains some mustache placeholders: 

```java
package tech.jhipster.lite.generator.my_package;

public class Dummy {

  // ...
}
```

Those placeholders will be replaced by properties values during module application.

And this is it for this part of the documentation... Of course you can do a lot more than that in the `JHipsterModule` but the goal of this documentation is not to go deep in this usage! You have a lot of running example and you can always ask for help, we'll be really happy to help you provide your implementations!

## Creating JHipsterModuleResource

As the main goal of a `JHipsterModuleResource` is to expose a WebService let's start by creating a gherkin scenario for that. So in `src/test/features/my-module.feature` we'll do:

```
Feature: My module

  Scenario: Should apply my module
    When I apply "my-module" module to default project
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/my_package"
      | Dummy.java |
```

> The goal of this test is not to duplicate your factory unit test! Just ensure that one change done by your module is visible here, it is enough since we only want to ensure that the WebService is working as expected.

You can now run `CucumberTest` and ensure that it is failing as expected (with a 404).

To be used by JHLite, the `JHipsterModuleResource` needs to be a Spring bean so, let's create a configuration in `tech.jhipster.lite.generator.my_module.infrastructure.primary`:

```java
@Configuration
class MyModuleModuleConfiguration {

  @Bean
  JHipsterModuleResource myModule(
    MyModuleApplicationService myModules
  ) {
    return JHipsterModuleResource
      .builder()
      .slug("my-module")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Group", "This is my module")
      .standalone()
      .tags("server")
      .factory(myModules::buildModule);
  }
}
```

In fact, you don't really have choices here, the `JHipsterModuleResource.builder()` is fluent and will only let you go to the next possible step. The most confusing one may be the last one `.factory(myModules::buildModule)` which is, in fact, a method called to build the module.

For this to work, we'll need to add a simple orchestration class in `tech.jhipster.lite.generator.my_module.application`:

```java
@Service
public class MyModuleApplicationService {

  private final MyModuleFactory factory;

  public MyModuleApplicationService() {
    factory = new MyModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}

```

In your `JHipsterModuleResource` you can define additional properties and an organization to display your module in the landscape (replacing `.standalone()`). Here again, you have a lot of example to rely on.

## Applying module in CI

Now that your are confident about your module action you can add it to the JHLite ci by adding it in the `fullapp` application in [generate.sh](../tests-ci/generate.sh) so it will be compiled and analyzed by SonarQube. You can also create a brand new app if needed.
