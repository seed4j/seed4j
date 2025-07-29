package com.seed4j.statistic.infrastructure.primary;

import static com.seed4j.cucumber.rest.CucumberRestAssertions.assertThatLastResponse;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class StatsSteps {

  @Autowired
  private TestRestTemplate rest;

  @When("I get statistics")
  public void getStatistics() {
    rest.getForEntity("/api/statistics", Void.class);
  }

  @When("I get statistics with criteria")
  public void getStatisticsWithCriteria(Map<String, String> criteria) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/api/statistics");
    criteria.forEach(builder::queryParam);
    String url = builder.toUriString();
    rest.getForEntity(url, Void.class);
  }

  @Then("I should have statistics")
  public void shouldHaveStatistics(Map<String, Object> statistics) {
    assertThatLastResponse().hasOkStatus().hasResponse().containing(statistics);
  }
}
