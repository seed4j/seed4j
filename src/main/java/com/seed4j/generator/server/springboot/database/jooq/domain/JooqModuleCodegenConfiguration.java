package com.seed4j.generator.server.springboot.database.jooq.domain;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;

final class JooqModuleCodegenConfiguration {

  private final String databaseUrl;
  private final String user;
  private final String inputSchema;
  private final String jooqMetaDatabase;
  private final String password;

  private JooqModuleCodegenConfiguration(JooqModuleCodegenConfigurationBuilder builder) {
    Assert.notNull("databaseUrl", builder.databaseUrl);
    Assert.notNull("user", builder.user);
    Assert.notNull("inputSchema", builder.inputSchema);
    Assert.notNull("jooqMetaDatabase", builder.jooqMetaDatabase);

    this.databaseUrl = builder.databaseUrl;
    this.user = builder.user;
    this.inputSchema = builder.inputSchema;
    this.jooqMetaDatabase = builder.jooqMetaDatabase;
    this.password = Optional.ofNullable(builder.password).orElse("");
  }

  public static JooqModuleCodegenConfigurationDatabaseUrlBuilder builder() {
    return new JooqModuleCodegenConfigurationBuilder();
  }

  public String getConfiguration() {
    return String.format(
      """
      <jdbc>
        <url>%s</url>
        <user>%s</user>
        <password>%s</password>
      </jdbc>
      <generator>
        <database>
          <name>%s</name>
          <includes>.*</includes>
          <inputSchema>%s</inputSchema>
        </database>
        <target>
          <packageName>org.jooq.codegen</packageName>
          <directory>target/generated-sources/jooq</directory>
        </target>
      </generator>
      """,
      databaseUrl,
      user,
      password,
      jooqMetaDatabase,
      inputSchema
    );
  }

  public sealed interface JooqModuleCodegenConfigurationDatabaseUrlBuilder permits JooqModuleCodegenConfigurationBuilder {
    JooqModuleCodegenConfigurationUserBuilder databaseUrl(String databaseUrl);
  }

  public sealed interface JooqModuleCodegenConfigurationUserBuilder permits JooqModuleCodegenConfigurationBuilder {
    JooqModuleCodegenConfigurationInputSchemaBuilder user(String user);
  }

  public sealed interface JooqModuleCodegenConfigurationInputSchemaBuilder permits JooqModuleCodegenConfigurationBuilder {
    JooqModuleCodegenConfigurationJooqMetaDatabaseBuilder inputSchema(String inputSchema);
  }

  public sealed interface JooqModuleCodegenConfigurationJooqMetaDatabaseBuilder permits JooqModuleCodegenConfigurationBuilder {
    JooqModuleCodegenConfigurationOptionalBuilder jooqMetaDatabase(String jooqMetaDatabase);
  }

  public sealed interface JooqModuleCodegenConfigurationOptionalBuilder permits JooqModuleCodegenConfigurationBuilder {
    JooqModuleCodegenConfigurationOptionalBuilder password(String password);

    JooqModuleCodegenConfiguration build();
  }

  private static final class JooqModuleCodegenConfigurationBuilder
    implements
      JooqModuleCodegenConfigurationDatabaseUrlBuilder,
      JooqModuleCodegenConfigurationUserBuilder,
      JooqModuleCodegenConfigurationInputSchemaBuilder,
      JooqModuleCodegenConfigurationJooqMetaDatabaseBuilder,
      JooqModuleCodegenConfigurationOptionalBuilder
  {

    private String databaseUrl;
    private String user;
    private String inputSchema;
    private String jooqMetaDatabase;
    private String password;

    @Override
    public JooqModuleCodegenConfigurationUserBuilder databaseUrl(String databaseUrl) {
      this.databaseUrl = databaseUrl;

      return this;
    }

    @Override
    public JooqModuleCodegenConfigurationInputSchemaBuilder user(String user) {
      this.user = user;

      return this;
    }

    @Override
    public JooqModuleCodegenConfigurationJooqMetaDatabaseBuilder inputSchema(String inputSchema) {
      this.inputSchema = inputSchema;

      return this;
    }

    @Override
    public JooqModuleCodegenConfigurationOptionalBuilder jooqMetaDatabase(String jooqMetaDatabase) {
      this.jooqMetaDatabase = jooqMetaDatabase;

      return this;
    }

    @Override
    public JooqModuleCodegenConfigurationOptionalBuilder password(String password) {
      this.password = password;

      return this;
    }

    @Override
    public JooqModuleCodegenConfiguration build() {
      return new JooqModuleCodegenConfiguration(this);
    }
  }
}
