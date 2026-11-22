package com.seed4j.cucumber;

import com.seed4j.Seed4JApp;
import com.seed4j.cucumber.rest.CucumberRestTestContext;
import com.seed4j.project.infrastructure.secondary.FakedFileSystemProjectFilesConfiguration;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.resttestclient.autoconfigure.RestTestClientBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@CucumberContextConfiguration
@AutoConfigureRestTestClient
@SpringBootTest(
  classes = {
    Seed4JApp.class,
    FakedFileSystemProjectFilesConfiguration.class,
    ServerCucumberConfiguration.CucumberRestTestClientConfiguration.class,
  },
  webEnvironment = WebEnvironment.RANDOM_PORT
)
public class ServerCucumberConfiguration {

  @Before
  public void resetTestContext() {
    CucumberRestTestContext.reset();
  }

  @TestConfiguration
  static class CucumberRestTestClientConfiguration {

    @Bean
    RestTestClientBuilderCustomizer cucumberRestTestClientCustomizer() {
      return builder ->
        builder
          .requestInterceptor(saveLastResultInterceptor())
          .configureMessageConverters(converters -> converters.withStringConverter(new StringHttpMessageConverter(StandardCharsets.UTF_8)));
    }

    private static ClientHttpRequestInterceptor saveLastResultInterceptor() {
      return (request, body, execution) -> {
        ClientHttpResponse response = execution.execute(request, body);

        CucumberRestTestContext.addResponse(request, response, execution, body);

        return response;
      };
    }
  }
}
