# Applying modules

A module adds one technical capability to a project: a build tool, a framework, a database, a linter... You build a project by applying modules, all at once or one after the other, and you can come back later to apply more.

This guide explains how to **apply** modules. To **create** one, see [Creating a Seed4J module](module-creation.md).

## Before you start

Run Seed4J on your machine (see the [Quick Start](../README.md#quick-start)), then open http://localhost:1339.

If your project already exists, commit your work first: applying a module modifies the files of the project, and the differences are much easier to review from a clean git state.

## Your first project

In the **Landscape** (http://localhost:1339/landscape):

1. Fill in the **Project folder**. Seed4J suggests a new temporary folder: replace it with the folder you want to use.
2. Click on the modules you want. For a minimal Java project, select `init`, `maven-java` and `java-base`.
3. Check the properties in the side panel. The grey values are the defaults, used when you leave a field empty.
4. Keep **Commit module application** checked to get one git commit for each module.
5. Click on **Apply new**.

The code is now in your folder. With the commit option, `git log` gives:

```
Apply module: java-base
Apply module: maven-java
Apply module: init
```

> [!NOTE]
> With the [Docker image](../README.md#dockerpodman-quickstart), the project folder must be a direct child of `/tmp/seed4j`, such as `/tmp/seed4j/my-app`. As your current folder is mounted on `/tmp/seed4j`, the project is generated in `./my-app`.

## Choosing modules

The landscape shows the modules and their dependencies, from left to right:

- selecting a module also selects the modules it depends on;
- unselecting a module also unselects the modules depending on it;
- the modules grouped in a frame are alternatives for the same feature (`maven-java` and `gradle-java`, for example): you can only select one of them. When a module depends on such a feature, you have to choose the alternative yourself.

To find your way:

- **search** for a module with the search field;
- filter by **rank**, from D (experimental) to S (production-proven), to know how much a module has been used;
- use a **preset** (cog button) to select a ready-made list of modules, such as `Webapp: Vue + Spring Boot`. It replaces your current selection.

The **Patches** page (http://localhost:1339/patches) lists the same modules by category and applies them one at a time. It does not apply the dependencies for you: prefer the landscape if you don't know them.

## Properties

Each module declares the properties it needs. The most common ones:

| Property             | Description                            | Default                     |
| -------------------- | -------------------------------------- | --------------------------- |
| `projectName`        | Full name of the project               | `Seed4J Sample Application` |
| `baseName`           | Short name, only letters and numbers   | `seed4jSampleApplication`   |
| `packageName`        | Base Java package                      | `com.mycompany.myapp`       |
| `nodePackageManager` | Node package manager (`npm` or `pnpm`) | `npm`                       |
| `indentSize`         | Number of spaces of the indentation    | `2`                         |
| `endOfLine`          | Type of line break (`lf` or `crlf`)    | `lf`                        |

Choose them carefully for the first modules, and keep the same values afterwards: changing `packageName` later does not move the existing code.

## Adding modules to an existing project

Enter the folder of the project in **Project folder** and leave the field. Seed4J reads the history of the project, marks the modules already applied and restores their properties. Then:

- **Apply new** only applies the selected modules which are not applied yet;
- **Apply selected** applies all the selected modules, including the ones already applied.

The history is in the `.seed4j/modules` folder of the project, with one JSON file for each application. Commit this folder with your code: Seed4J needs it to know the state of the project.

## Applying a module again

You can apply a module again, with the arrow icon of an applied module, to get its latest version.

> [!WARNING]
> Applying a module again **overwrites the files it generates**, without asking for confirmation: your changes in these files are lost if they are not committed.

Commit your work, uncheck **Commit module application**, apply the module, then use `git diff` to keep only the changes you want.

## Using the REST API

Everything the interface does is available with the REST API, documented at http://localhost:1339/swagger-ui/index.html.

List the available modules, with their properties:

```bash
curl http://localhost:1339/api/modules
```

Apply modules:

```bash
curl -X POST http://localhost:1339/api/apply-patches \
  -H "Content-Type: application/json" \
  -d '{
    "modules": ["init", "maven-java", "java-base"],
    "properties": {
      "projectFolder": "/tmp/my-app",
      "commit": true,
      "parameters": {
        "projectName": "My App",
        "baseName": "myapp",
        "packageName": "com.mycompany.myapp"
      }
    }
  }'
```

Get the modules applied to a project:

```bash
curl -H "Accept: application/json" "http://localhost:1339/api/projects?path=/tmp/my-app"
```

When applying modules with the API:

- the modules are applied in the order of the landscape, whatever their order in the request;
- the missing dependencies are **not** added: list all the modules you need;
- the unknown slugs are ignored, without any error.

To apply modules from your own Java application, see [seed4j-engine](../seed4j-engine/README.md).

## When something goes wrong

**The apply buttons are disabled.** No module is selected, the project folder is empty, or a mandatory property without default value is missing.

**A module can't be selected.** It depends on a feature with several alternatives: select one of them first. It can also be incompatible with an alternative you already selected or applied.

**`Missing java build tool configuration files`.** The module needs a Java build tool: apply `maven-java` or `gradle-java` before.

**The application stopped in the middle.** Modules are applied one by one, and the ones applied before the error are kept. Look at the Seed4J logs, fix the problem, then apply the remaining modules.

**My own files are in the commit of a module.** The commit contains all the pending changes of the project, not only the ones of the module. Commit your work before applying modules.

**I want to remove a module.** Seed4J does not remove modules. If you kept one commit for each module, use `git revert` on its commit, which also removes it from the history.
