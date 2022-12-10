package tech.jhipster.lite.dsl.parser.domain;

public interface DslRepository {
  JhipsterDslFileIdentifiant createDslFile(JhipsterDslFileToSave file);

  DslApplication parseDsl(JhipsterDslFileIdentifiant file);
}
