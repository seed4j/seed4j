package tech.jhipster.lite.generator.server.springboot.database.mssql.domain;

import static tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonModuleBuilder.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.generator.server.springboot.database.common.domain.DatabaseType;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class MsSQLModuleFactory {

  private final DockerImages dockerImages;

  public MsSQLModuleFactory(DockerImages dockerImages) {
    Assert.notNull("dockerImages", dockerImages);

    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    DockerImageVersion dockerImage = dockerImages.get("mcr.microsoft.com/mssql/server");
    JHipsterSource source = from("server/springboot/database/" + DatabaseType.MSSQL.id());

    //@formatter:off
    return sqlCommonModuleBuilder(properties, DatabaseType.MSSQL, dockerImage, documentationTitle("MsSQL"), artifactId("mssqlserver"))
      .files()
        .add(source.template("container-license-acceptance.txt"), to("src/test/resources/container-license-acceptance.txt"))
        .add(
          source.template("MsSQLTestContainerExtension.java"),
          toSrcTestJava().append(properties.basePackage().path()).append("MsSQLTestContainerExtension.java")
        )
        .and()
      .javaDependencies()
        .addDependency(javaDependency().groupId("com.microsoft.sqlserver").artifactId("mssql-jdbc").scope(JavaDependencyScope.RUNTIME).build())
        .and()
      .springMainProperties()
        .set(
          propertyKey("spring.datasource.url"),
          propertyValue("jdbc:sqlserver://localhost:1433;database=" + properties.projectBaseName().name() + ";trustServerCertificate=true")
        )
        .set(propertyKey("spring.datasource.username"), propertyValue("SA"))
        .set(propertyKey("spring.datasource.password"), propertyValue("yourStrong(!)Password"))
        .set(propertyKey("spring.datasource.driver-class-name"), propertyValue("com.microsoft.sqlserver.jdbc.SQLServerDriver"))
        .set(propertyKey("spring.jpa.hibernate.ddl-auto"), propertyValue("update"))
        .set(propertyKey("spring.jpa.properties.hibernate.criteria.literal_handling_mode"), propertyValue("BIND"))
        .set(propertyKey("spring.jpa.properties.hibernate.dialect"), propertyValue("org.hibernate.dialect.SQLServer2012Dialect"))
        .set(propertyKey("spring.jpa.properties.hibernate.format_sql"), propertyValue("true"))
        .set(propertyKey("spring.jpa.properties.hibernate.jdbc.fetch_size"), propertyValue("150"))
        .and()
      .springTestProperties()
        .set(
          propertyKey("spring.datasource.url"),
          propertyValue(
            "jdbc:tc:sqlserver:///;database=" + properties.projectBaseName().name() + ";trustServerCertificate=true?TC_TMPFS=/testtmpfs:rw"
          )
        )
        .set(propertyKey("spring.datasource.username"), propertyValue("SA"))
        .set(propertyKey("spring.datasource.password"), propertyValue("yourStrong(!)Password"))
        .and()
      .mandatoryReplacements()
        .in(path("src/test/java").append(properties.basePackage().path()).append("IntegrationTest.java"))
          .add(
            lineBeforeText("import org.springframework.boot.test.context.SpringBootTest;"),
            "import org.junit.jupiter.api.extension.ExtendWith;"
          )
          .add(lineBeforeText("public @interface"), "@ExtendWith(MsSQLTestContainerExtension.class)")
          .and()
        .and()
      .springMainLogger("com.microsoft.sqlserver", LogLevel.WARN)
      .springMainLogger("org.reflections", LogLevel.WARN)
      .build();
    //@formatter:on
  }
}
