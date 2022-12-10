package tech.jhipster.lite.dsl.common.domain;

import tech.jhipster.lite.dsl.generator.java.clazz.domain.DslJavaGenerator;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.dsl.parser.domain.DslParser;
import tech.jhipster.lite.dsl.parser.domain.JhipsterDslFileToImport;
import tech.jhipster.lite.error.domain.Assert;

public class DslGenerator {

  private final DslParser dslParser;
  private final DslJavaGenerator dslJavaGenerator;

  public DslGenerator(DslParser dslParser, DslJavaGenerator dslJavaGenerator) {
    Assert.notNull("dslParser", dslParser);
    Assert.notNull("dslJavaGenerator", dslJavaGenerator);

    this.dslParser = dslParser;
    this.dslJavaGenerator = dslJavaGenerator;
  }

  public void importAndGenerate(JhipsterDslFileToImport dslToImport) {
    DslApplication dslApplication = this.dslParser.importDsl(dslToImport);

    this.dslJavaGenerator.generate(dslApplication);
  }
}
