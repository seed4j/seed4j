package com.seed4j.generator.client.tikui.domain;

import static com.seed4j.module.domain.JHipsterModule.LINE_BREAK;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.lineAfterText;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.packageName;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.preCommitCommands;
import static com.seed4j.module.domain.JHipsterModule.scriptCommand;
import static com.seed4j.module.domain.JHipsterModule.scriptKey;
import static com.seed4j.module.domain.JHipsterModule.stagedFilesFilter;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.nodejs.JHLiteNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.module.domain.replacement.RegexNeedleAfterReplacer;
import com.seed4j.module.domain.replacement.ReplacementCondition;
import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;

public class TikuiModuleFactory {

  private static final SeedSource SOURCE = from("client/tikui");
  private static final SeedSource STYLE_SOURCE = SOURCE.append("style");

  private static final SeedDestination STYLE_DESTINATION = to("src/main/style");

  private static final String ATOM = "atom";
  private static final String ATOM_BUTTON = ATOM + "/button";
  private static final String ATOM_INPUT_TEXT = ATOM + "/input-text";
  private static final String ATOM_LABEL = ATOM + "/label";
  private static final String ATOM_PAGE_TITLE = ATOM + "/page-title";
  private static final String ATOM_PARAGRAPH = ATOM + "/paragraph";
  private static final String MOLECULE = "molecule";
  private static final String MOLECULE_FIELD = MOLECULE + "/field";
  private static final String MOLECULE_TOAST = MOLECULE + "/toast";
  private static final String ORGANISM = "organism";
  private static final String ORGANISM_COLUMNS = ORGANISM + "/columns";
  private static final String ORGANISM_LINES = ORGANISM + "/lines";
  private static final String ORGANISM_TOASTS = ORGANISM + "/toasts";
  private static final String TEMPLATE = "template";
  private static final String TEMPLATE_TEMPLATE_PAGE = TEMPLATE + "/template-page";
  private static final String TEMPLATE_TOASTING = TEMPLATE + "/toasting";
  private static final String QUARK = "quark";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .preCommitActions(stagedFilesFilter("*.pug"), preCommitCommands("prettier --write"))
      .preCommitActions(stagedFilesFilter("*.{css,scss}"), preCommitCommands("stylelint --fix --allow-empty-input", "prettier --write"))
      .packageJson()
        .addDependency(packageName("@tikui/core"), COMMON)
        .addDevDependency(packageName("@prettier/plugin-pug"), COMMON)
        .addDevDependency(packageName("sass"), COMMON)
        .addDevDependency(packageName("stylelint"), COMMON)
        .addDevDependency(packageName("stylelint-config-concentric-order"), COMMON)
        .addDevDependency(packageName("stylelint-config-standard-scss"), COMMON)
        .addDevDependency(packageName("stylelint-order"), COMMON)
        .addDevDependency(packageName("tikuidoc-tikui"), COMMON)
        .addScript(scriptKey("build:tikui"), scriptCommand("tikui-core build"))
        .addScript(scriptKey("dev:tikui"), scriptCommand("tikui-core serve"))
        .and()
      .files()
        .add(SOURCE.template("tikuiconfig.json"), to("tikuiconfig.json"))
        .add(SOURCE.template(".stylelintrc.json"), to(".stylelintrc.json"))
        .batch(STYLE_SOURCE, STYLE_DESTINATION)
          .addTemplate("tikui.scss")
          .addTemplate("favicon.ico")
          .addTemplate("index.pug")
          .addTemplate("layout.pug")
          .addTemplate("layout-documentation.pug")
          .and()
        .batch(STYLE_SOURCE.append(ATOM), STYLE_DESTINATION.append(ATOM))
          .addTemplate("_atom.scss")
          .addTemplate("atom.pug")
          .and()
        .batch(STYLE_SOURCE.append(ATOM_BUTTON), STYLE_DESTINATION.append(ATOM_BUTTON))
          .addTemplates(componentFiles("button"))
          .and()
        .batch(STYLE_SOURCE.append(ATOM_INPUT_TEXT), STYLE_DESTINATION.append(ATOM_INPUT_TEXT))
          .addTemplates(componentFiles("input-text"))
          .and()
        .batch(STYLE_SOURCE.append(ATOM_LABEL), STYLE_DESTINATION.append(ATOM_LABEL))
          .addTemplates(componentFiles("label"))
          .and()
        .batch(STYLE_SOURCE.append(ATOM_PAGE_TITLE), STYLE_DESTINATION.append(ATOM_PAGE_TITLE))
          .addTemplates(componentFiles("page-title"))
          .and()
        .batch(STYLE_SOURCE.append(ATOM_PARAGRAPH), STYLE_DESTINATION.append(ATOM_PARAGRAPH))
          .addTemplates(componentFiles("paragraph"))
          .and()
        .batch(STYLE_SOURCE.append(MOLECULE), STYLE_DESTINATION.append(MOLECULE))
          .addTemplate("_molecule.scss")
          .addTemplate("molecule.pug")
          .and()
        .batch(STYLE_SOURCE.append(MOLECULE_FIELD), STYLE_DESTINATION.append(MOLECULE_FIELD))
          .addTemplates(componentFiles("field"))
          .and()
        .batch(STYLE_SOURCE.append(MOLECULE_TOAST), STYLE_DESTINATION.append(MOLECULE_TOAST))
          .addTemplates(componentFiles("toast"))
          .and()
        .batch(STYLE_SOURCE.append(ORGANISM), STYLE_DESTINATION.append(ORGANISM))
          .addTemplate("_organism.scss")
          .addTemplate("organism.pug")
          .and()
        .batch(STYLE_SOURCE.append(ORGANISM_COLUMNS), STYLE_DESTINATION.append(ORGANISM_COLUMNS))
          .addTemplates(componentFiles("columns"))
          .and()
        .batch(STYLE_SOURCE.append(ORGANISM_LINES), STYLE_DESTINATION.append(ORGANISM_LINES))
          .addTemplates(componentFiles("lines"))
          .and()
        .batch(STYLE_SOURCE.append(ORGANISM_TOASTS), STYLE_DESTINATION.append(ORGANISM_TOASTS))
          .addTemplates(componentFiles("toasts"))
          .and()
        .batch(STYLE_SOURCE.append(QUARK), STYLE_DESTINATION.append(QUARK))
          .addTemplate("_placeholder.scss")
          .addTemplate("_spacing.scss")
          .and()
        .batch(STYLE_SOURCE.append(TEMPLATE), STYLE_DESTINATION.append(TEMPLATE))
          .addTemplate("_template.scss")
          .addTemplate("template.pug")
          .and()
        .batch(STYLE_SOURCE.append(TEMPLATE_TEMPLATE_PAGE), STYLE_DESTINATION.append(TEMPLATE_TEMPLATE_PAGE))
          .addTemplates(componentFiles("template-page"))
          .and()
        .batch(STYLE_SOURCE.append(TEMPLATE_TOASTING), STYLE_DESTINATION.append(TEMPLATE_TOASTING))
          .addTemplates(componentFiles("toasting"))
          .and()
        .batch(STYLE_SOURCE.append("token"), STYLE_DESTINATION.append("token"))
          .addTemplate("spacing/_spacings.scss")
          .addTemplate("spacing/_vars.scss")
          .addTemplate("_animations.scss")
          .addTemplate("_color.scss")
          .addTemplate("_font.scss")
          .addTemplate("_gradient.scss")
          .addTemplate("_radius.scss")
          .addTemplate("_size.scss")
          .addTemplate("_token.scss")
          .and()
        .batch(STYLE_SOURCE.append("variables"), STYLE_DESTINATION.append("variables"))
          .addTemplate("_breakpoint.scss")
          .and()
        .and()
      .optionalReplacements()
        .in(path("vite.config.ts"))
          .add(existingProxyReplacer(), proxyForStyle(properties))
          .add(newProxyReplacer(), newProxyForStyle(properties))
          .and()
        .in(path("proxy.conf.json"))
          .add(lineBeforeText("\"/api\":"), styleProxyConf(properties))
          .and()
        .in(path("src/main/webapp/index.html"))
          .add(lineBeforeText("</head>"), tikuiLink(properties))
          .and()
        .in(path(".prettierrc"))
          .add(lineAfterText("plugins:"), pugPlugin(properties))
          .and()
        .and()
      .gitIgnore()
        .pattern(".tikui-cache")
        .and()
      .build();
    // @formatter:on
  }

  private static Collection<String> componentFiles(String name) {
    return Set.of("_" + name + ".scss", name + ".code.pug", name + ".md", name + ".mixin.pug", name + ".render.pug");
  }

  private static RegexNeedleAfterReplacer newProxyReplacer() {
    return new RegexNeedleAfterReplacer(ReplacementCondition.notContaining("proxy:"), Pattern.compile("port:\\s*9000,", Pattern.MULTILINE));
  }

  private static RegexNeedleAfterReplacer existingProxyReplacer() {
    return new RegexNeedleAfterReplacer(
      (contentBeforeReplacement, replacement) -> contentBeforeReplacement.contains("proxy:"),
      Pattern.compile("proxy:\\s*\\{", Pattern.MULTILINE)
    );
  }

  private String tikuiLink(JHipsterModuleProperties properties) {
    return properties.indentation().times(2) + "<link rel=\"stylesheet\" href=\"/style/tikui.css\" />";
  }

  private String pugPlugin(JHipsterModuleProperties properties) {
    return properties.indentation().times(1) + "- '@prettier/plugin-pug'";
  }

  private static String newProxyForStyle(JHipsterModuleProperties properties) {
    return new StringBuilder()
      .append(properties.indentation().times(2))
      .append("proxy: {")
      .append(LINE_BREAK)
      .append(proxyForStyle(properties))
      .append(LINE_BREAK)
      .append(properties.indentation().times(2))
      .append("},")
      .toString();
  }

  private static String proxyForStyle(JHipsterModuleProperties properties) {
    return """
    {S}{S}{S}'/style': {
    {S}{S}{S}{S}ws: true,
    {S}{S}{S}{S}changeOrigin: true,
    {S}{S}{S}{S}rewrite: path => path.replace('/style', ''),
    {S}{S}{S}{S}target: 'http://localhost:9005',
    {S}{S}{S}},""".replace("{S}", properties.indentation().times(1));
  }

  private String styleProxyConf(JHipsterModuleProperties properties) {
    return """
    {S}"/style": {
    {S}{S}"target": "http://localhost:9005",
    {S}{S}"secure": false
    {S}},
    """.replace("{S}", properties.indentation().times(1));
  }
}
