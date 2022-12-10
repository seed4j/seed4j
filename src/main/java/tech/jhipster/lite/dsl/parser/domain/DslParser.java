package tech.jhipster.lite.dsl.parser.domain;

import tech.jhipster.lite.error.domain.Assert;

public class DslParser {

  private final DslRepository dslRepository;

  public DslParser(DslRepository dslRepository) {
    Assert.notNull("dslSaveRepository", dslRepository);
    this.dslRepository = dslRepository;
  }

  public DslApplication importDsl(JhipsterDslFileToImport dslToImport) {
    Assert.notNull("dslToImport", dslToImport);

    // save file in project folder
    JhipsterDslFileIdentifiant fileIdentifiant = this.dslRepository.createDslFile(new JhipsterDslFileToSave(dslToImport));

    return this.dslRepository.parseDsl(fileIdentifiant);
    // load and parse
    // generate file

  }
}
