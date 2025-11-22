package com.seed4j.cucumber;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@TestConfiguration
public class CucumberTestRestTemplateConfiguration {

  @Bean
  @Lazy
  public TestRestTemplate testRestTemplate(@LocalServerPort int port) {
    RestTemplateBuilder builder = new RestTemplateBuilder().rootUri("http://localhost:" + port);
    return new TestRestTemplate(builder);
  }
}
