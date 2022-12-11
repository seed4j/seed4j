package tech.jhipster.lite.dsl.parser.domain;

import tech.jhipster.lite.dsl.common.domain.DslProperties;

public interface DslRepository {
  JhipsterDslFileIdentifiant createDslFile(JhipsterDslFileToSave file);

  DslApplication parseDsl(JhipsterDslFileIdentifiant file, DslProperties properties);
}
