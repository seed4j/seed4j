package tech.jhipster.lite.dsl.parser.domain;

import tech.jhipster.lite.dsl.common.domain.DslProperties;
import tech.jhipster.lite.error.domain.Assert;

public class DslParser {

  private final DslRepository dslRepository;

  public DslParser(DslRepository dslRepository) {
    Assert.notNull("dslSaveRepository", dslRepository);
    this.dslRepository = dslRepository;
  }

  public DslApplication importDsl(JhipsterDslFileToImport dslToImport, DslProperties properties) {
    Assert.notNull("dslToImport", dslToImport);
    Assert.notNull("properties", properties);

    // save file in project folder
    JhipsterDslFileIdentifiant fileIdentifiant = this.dslRepository.createDslFile(new JhipsterDslFileToSave(dslToImport));

    return this.dslRepository.parseDsl(fileIdentifiant, properties);
    // load and parse
    // generate file

  }
}
