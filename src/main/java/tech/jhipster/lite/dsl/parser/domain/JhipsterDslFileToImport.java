package tech.jhipster.lite.dsl.parser.domain;

import java.util.Arrays;

public class JhipsterDslFileToImport {

  public static DslFileImportBuilder builder() {
    return new DslFileImportBuilder();
  }

  private String name;

  private String originalFilename;

  private byte[] bytes;

  public String getName() {
    return name;
  }

  public String getOriginalFilename() {
    return originalFilename;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public static final class DslFileImportBuilder {

    private byte[] bytes;
    private String name;
    private String originalFilename;

    private DslFileImportBuilder() {}

    public DslFileImportBuilder bytes(byte[] bytes) {
      this.bytes = bytes;
      return this;
    }

    public DslFileImportBuilder name(String name) {
      this.name = name;
      return this;
    }

    public DslFileImportBuilder originalFilename(String originalFilename) {
      this.originalFilename = originalFilename;
      return this;
    }

    public JhipsterDslFileToImport build() {
      JhipsterDslFileToImport jhipsterDslFileToImport = new JhipsterDslFileToImport();
      jhipsterDslFileToImport.bytes = this.bytes;
      jhipsterDslFileToImport.name = this.name;
      jhipsterDslFileToImport.originalFilename = this.originalFilename;
      return jhipsterDslFileToImport;
    }
  }
}
