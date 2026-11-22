package com.seed4j.statistic.infrastructure.primary;

import static com.seed4j.cucumber.rest.CucumberRestAssertions.assertThatLastResponse;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.util.UriComponentsBuilder;

public class ServerStatsSteps {

  @Autowired
  private RestTestClient rest;

  @When("I get statistics")
  public void getStatistics() {
    rest.get().uri("/api/statistics").exchange();
  }

  @When("I get statistics with criteria")
  public void getStatisticsWithCriteria(Map<String, String> criteria) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/api/statistics");
    criteria.forEach(builder::queryParam);
    String url = builder.toUriString();
    rest.get().uri(url).exchange();
  }

  @Then("I should have statistics")
  public void shouldHaveStatistics(Map<String, Object> statistics) {
    assertThatLastResponse().hasOkStatus().hasResponse().containing(statistics);
  }
}
