# seed4j-engine

The engine applying Seed4J modules to projects. Use it to build your own Seed4J application, such as a command line.

## Dependencies

```xml
<dependency>
  <groupId>com.seed4j</groupId>
  <artifactId>seed4j-engine</artifactId>
  <version>${seed4j.version}</version>
</dependency>
<dependency>
  <!-- the official modules, add the extensions you want to use next to it -->
  <groupId>com.seed4j</groupId>
  <artifactId>seed4j-core-modules</artifactId>
  <version>${seed4j.version}</version>
</dependency>
```

## Start the engine

The engine is a Spring Boot auto-configuration: any Spring Boot application gets it, there is nothing to scan nor to import.

```java
@SpringBootApplication
public class MyCliApp {

  public static void main(String[] args) {
    SpringApplication.run(MyCliApp.class, args);
  }
}
```

The application needs at least one module: the official modules, or an extension.

## Apply modules

Inject `Seed4JModulesApplicationService`:

```java
@Component
class ApplyCommand {

  private final Seed4JModulesApplicationService modules;

  ApplyCommand(Seed4JModulesApplicationService modules) {
    this.modules = modules;
  }

  void apply(String projectFolder) {
    // true: commit each applied module in the project git repository
    Seed4JModuleProperties properties = new Seed4JModuleProperties(projectFolder, true, Map.of("baseName", "myapp"));

    modules.apply(new Seed4JModulesToApply(List.of(new Seed4JModuleSlug("init"), new Seed4JModuleSlug("maven-java")), properties));
  }
}
```

It also gives the available modules (`resources()`), their landscape (`landscape()`) and the presets (`getPresets()`).

The modules applied to a project are recorded in its `.seed4j/modules` folder. `JavaProjectHistories` reads them back.

Each applied module is published as a `Seed4JModuleApplied` Spring application event.

## Configuration

| Property                        | Description                                      | Default   |
| ------------------------------- | ------------------------------------------------ | --------- |
| `seed4j.hidden-resources.slugs` | Slugs of the modules to hide                     | none      |
| `seed4j.hidden-resources.tags`  | Tags of the modules to hide                      | none      |
| `seed4j.preset-folder`          | Classpath folder of the presets (`*.json` files) | `presets` |
