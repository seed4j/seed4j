package com.seed4j.generator.server.springboot.database.jooq.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JooqModuleFactoryTest {

  private final JooqModuleFactory factory = new JooqModuleFactory();

  @Test
  void shouldBuildPostgreSQLModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildPostgreSQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-jooq</artifactId>
            </dependency>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.jooq</groupId>
                <artifactId>jooq-codegen-maven</artifactId>
                <version>${jooq.version}</version>
                <executions>
                  <execution>
                    <id>jooq-codegen</id>
                    <phase>generate-resources</phase>
                    <goals>
                      <goal>generate</goal>
                    </goals>
                  </execution>
                </executions>
                <configuration>
                  <jdbc>
                    <url>jdbc:postgresql://localhost:5432/myapp</url>
                    <user>myapp</user>
                    <password></password>
                  </jdbc>
                  <generator>
                    <database>
                      <name>org.jooq.meta.postgres.PostgresDatabase</name>
                      <includes>.*</includes>
                      <inputSchema>public</inputSchema>
                    </database>
                    <target>
                      <packageName>org.jooq.codegen</packageName>
                      <directory>target/generated-sources/jooq</directory>
                    </target>
                  </generator>
                </configuration>
              </plugin>
        """
      );
  }

  @Test
  void shouldBuildMariadbModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildMariaDB(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-jooq</artifactId>
            </dependency>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.jooq</groupId>
                <artifactId>jooq-codegen-maven</artifactId>
                <version>${jooq.version}</version>
                <executions>
                  <execution>
                    <id>jooq-codegen</id>
                    <phase>generate-resources</phase>
                    <goals>
                      <goal>generate</goal>
                    </goals>
                  </execution>
                </executions>
                <configuration>
                  <jdbc>
                    <url>jdbc:mariadb://localhost:3306/myapp</url>
                    <user>root</user>
                    <password></password>
                  </jdbc>
                  <generator>
                    <database>
                      <name>org.jooq.meta.mariadb.MariaDBDatabase</name>
                      <includes>.*</includes>
                      <inputSchema>myapp</inputSchema>
                    </database>
                    <target>
                      <packageName>org.jooq.codegen</packageName>
                      <directory>target/generated-sources/jooq</directory>
                    </target>
                  </generator>
                </configuration>
              </plugin>
        """
      );
  }

  @Test
  void shouldBuildMysqlModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildMySQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-jooq</artifactId>
            </dependency>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.jooq</groupId>
                <artifactId>jooq-codegen-maven</artifactId>
                <version>${jooq.version}</version>
                <executions>
                  <execution>
                    <id>jooq-codegen</id>
                    <phase>generate-resources</phase>
                    <goals>
                      <goal>generate</goal>
                    </goals>
                  </execution>
                </executions>
                <configuration>
                  <jdbc>
                    <url>jdbc:mysql://localhost:3306/myapp</url>
                    <user>root</user>
                    <password></password>
                  </jdbc>
                  <generator>
                    <database>
                      <name>org.jooq.meta.mysql.MySQLDatabase</name>
                      <includes>.*</includes>
                      <inputSchema>myapp</inputSchema>
                    </database>
                    <target>
                      <packageName>org.jooq.codegen</packageName>
                      <directory>target/generated-sources/jooq</directory>
                    </target>
                  </generator>
                </configuration>
              </plugin>
        """
      );
  }

  @Test
  void shouldBuildMssqlModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildMsSQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-jooq</artifactId>
            </dependency>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.jooq</groupId>
                <artifactId>jooq-codegen-maven</artifactId>
                <version>${jooq.version}</version>
                <executions>
                  <execution>
                    <id>jooq-codegen</id>
                    <phase>generate-resources</phase>
                    <goals>
                      <goal>generate</goal>
                    </goals>
                  </execution>
                </executions>
                <configuration>
                  <jdbc>
                    <url>jdbc:sqlserver://localhost:1433;database=myapp;trustServerCertificate=true</url>
                    <user>SA</user>
                    <password>yourStrong(!)Password</password>
                  </jdbc>
                  <generator>
                    <database>
                      <name>org.jooq.meta.sqlserver.SQLServerDatabase</name>
                      <includes>.*</includes>
                      <inputSchema>model</inputSchema>
                    </database>
                    <target>
                      <packageName>org.jooq.codegen</packageName>
                      <directory>target/generated-sources/jooq</directory>
                    </target>
                  </generator>
                </configuration>
              </plugin>
        """
      );
  }
}
