package tech.jhipster.lite.generator.client.tikui.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class TikuiModuleFactory {

  private static final JHipsterSource SOURCE = from("client/tikui");
  private static final JHipsterSource STYLE_SOURCE = SOURCE.append("style");

  private static final JHipsterDestination STYLE_DESTINATION = to("src/main/style");

  private static final String ATOM = "atom";
  private static final String ATOM_BUTTON = ATOM + "/button";
  private static final String ATOM_INPUT_TEXT = ATOM + "/input-text";
  private static final String ATOM_LABEL = ATOM + "/label";
  private static final String MOLECULE = "molecule";
  private static final String MOLECULE_FIELD = MOLECULE + "/field";
  private static final String ORGANISM = "organism";
  private static final String ORGANISM_LINES = ORGANISM + "/lines";
  private static final String QUARK = "quark";

  //@formatter:off
  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("@tikui/core"), COMMON)
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
          .addTemplate("_button.scss")
          .addTemplate("button.code.pug")
          .addTemplate("button.md")
          .addTemplate("button.mixin.pug")
          .addTemplate("button.render.pug")
          .and()
        .batch(STYLE_SOURCE.append(ATOM_INPUT_TEXT), STYLE_DESTINATION.append(ATOM_INPUT_TEXT))
          .addTemplate("_input-text.scss")
          .addTemplate("input-text.code.pug")
          .addTemplate("input-text.md")
          .addTemplate("input-text.mixin.pug")
          .addTemplate("input-text.render.pug")
          .and()
        .batch(STYLE_SOURCE.append(ATOM_LABEL), STYLE_DESTINATION.append(ATOM_LABEL))
          .addTemplate("_label.scss")
          .addTemplate("label.code.pug")
          .addTemplate("label.md")
          .addTemplate("label.mixin.pug")
          .addTemplate("label.render.pug")
          .and()
        .batch(STYLE_SOURCE.append(MOLECULE), STYLE_DESTINATION.append(MOLECULE))
          .addTemplate("_molecule.scss")
          .addTemplate("molecule.pug")
          .and()
        .batch(STYLE_SOURCE.append(MOLECULE_FIELD), STYLE_DESTINATION.append(MOLECULE_FIELD))
          .addTemplate("_field.scss")
          .addTemplate("field.code.pug")
          .addTemplate("field.md")
          .addTemplate("field.mixin.pug")
          .addTemplate("field.render.pug")
          .and()
        .batch(STYLE_SOURCE.append(ORGANISM), STYLE_DESTINATION.append(ORGANISM))
          .addTemplate("_organism.scss")
          .addTemplate("organism.pug")
          .and()
        .batch(STYLE_SOURCE.append(QUARK), STYLE_DESTINATION.append(QUARK))
          .addTemplate("_placeholder.scss")
          .addTemplate("_spacing.scss")
          .and()
        .batch(STYLE_SOURCE.append(ORGANISM_LINES), STYLE_DESTINATION.append(ORGANISM_LINES))
          .addTemplate("_lines.scss")
          .addTemplate("lines.code.pug")
          .addTemplate("lines.md")
          .addTemplate("lines.mixin.pug")
          .addTemplate("lines.render.pug")
          .and()
        .add(STYLE_SOURCE.template("template/template.pug"), STYLE_DESTINATION.append("template/template.pug"))
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
        .and()
      .optionalReplacements()
        .in(path("vite.config.ts"))
          .add(lineAfterRegex("port:\\s*9000,"), vueStyleProxy(properties))
          .and()
        .in(path("src/main/webapp/index.html"))
          .add(lineBeforeText("</head>"), tikuiLink(properties))
          .and()
        .and()
      .gitIgnore()
        .pattern(".tikui-cache")
        .and()
      .build();
    //@formatter:on
  }

  private String tikuiLink(JHipsterModuleProperties properties) {
    return properties.indentation().times(2) + "<link rel=\"stylesheet\" href=\"/style/tikui.css\" />";
  }

  private static String vueStyleProxy(JHipsterModuleProperties properties) {
    return """
    {S}{S}proxy: {
    {S}{S}{S}'/style': {
    {S}{S}{S}{S}ws: true,
    {S}{S}{S}{S}changeOrigin: true,
    {S}{S}{S}{S}rewrite: path => path.replace('/style', ''),
    {S}{S}{S}{S}target: 'http://localhost:9005',
    {S}{S}{S}},
    {S}{S}},""".replace("{S}", properties.indentation().times(1));
  }
}
