package tech.jhipster.lite.generator.enums.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.toSrcMainJava;
import static tech.jhipster.lite.module.domain.JHipsterModule.toSrcTestJava;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class EnumModuleFactory {

  private static final JHipsterSource SOURCE = from("enums");
  private static final String COMMON_DOMAIN = "common/domain";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
	    return moduleBuilder(properties)
	      .files()
	      .add(SOURCE.template("Enums.java"), toSrcMainJava().append(properties.packagePath()).append(COMMON_DOMAIN).append("Enums.java"))
			  .add(SOURCE.template("UnmappableEnumException.java"), toSrcMainJava().append(properties.packagePath()).append(COMMON_DOMAIN).append("UnmappableEnumException.java"))
        .add(SOURCE.template("EnumsTest.java"), toSrcTestJava().append(properties.packagePath()).append(COMMON_DOMAIN).append("EnumsTest.java"))
	      .and()
	      .build();
    //@formatter:on
  }
}
