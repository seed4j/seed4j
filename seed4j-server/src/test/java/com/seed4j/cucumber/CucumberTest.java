package com.seed4j.cucumber;

import static io.cucumber.junit.platform.engine.Constants.*;

import com.seed4j.ComponentTest;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectDirectories;
import org.junit.platform.suite.api.Suite;

@Suite
@ComponentTest
@IncludeEngines("cucumber")
@SuppressWarnings("java:S2187")
@SelectDirectories("src/test/features")
// only the server steps: seed4j-test provides other steps, for the modules
@ConfigurationParameter(
  key = GLUE_PROPERTY_NAME,
  value = "com.seed4j.cucumber,com.seed4j.generator,com.seed4j.module.infrastructure.primary,com.seed4j.project,com.seed4j.statistic,com.seed4j.wire"
)
@ConfigurationParameter(
  key = PLUGIN_PROPERTY_NAME,
  value = "pretty, json:target/cucumber/cucumber.json, html:target/cucumber/cucumber.htm, junit:target/cucumber/TEST-cucumber.xml"
)
public class CucumberTest {}
