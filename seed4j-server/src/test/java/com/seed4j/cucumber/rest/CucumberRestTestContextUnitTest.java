package com.seed4j.cucumber.rest;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.seed4j.UnitTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

@UnitTest
class CucumberRestTestContextUnitTest {

  @Test
  void shouldGetQueryInformation() {
    addQuery("{}");

    assertThat(CucumberRestTestContext.getStatus()).isEqualTo(HttpStatus.OK);
    assertThat(CucumberRestTestContext.getUri()).isEqualTo("http://localhost/");
    assertThat(CucumberRestTestContext.getResponse()).contains("{}");
  }

  @Test
  void shouldResetTestContext() {
    addQuery("{}");

    CucumberRestTestContext.reset();

    assertThatThrownBy(CucumberRestTestContext::getResponse).isExactlyInstanceOf(AssertionError.class).hasMessageContaining("empty");
  }

  @Test
  void shouldGetZeroEntriesForUnknownPath() {
    addQuery("{}");

    assertThat(CucumberRestTestContext.countEntries("$.dummy")).isZero();
  }

  @Test
  void shouldGetNulForUnknownElement() {
    addQuery("{\"element\":\"value\"}");

    assertThat(CucumberRestTestContext.getElement("dummy")).isNull();
  }

  @Test
  void shouldGetElement() {
    addQuery("{\"element\":\"value\"}");

    assertThat(CucumberRestTestContext.getElement("element")).isEqualTo("value");
  }

  @Test
  void shouldGetElementWithPath() {
    addQuery("/dummies", "{\"element\":\"old\"}");
    addQuery("/dummies", "{\"element\":\"value\"}");
    addQuery("/dumm", "{\"element\":\"dd\"}");
    addQuery("/employees", "{\"element\":\"test\"}");

    assertThat(CucumberRestTestContext.getElement("dummies", "element")).isEqualTo("value");
  }

  @Test
  void shouldGetOneEntryForSingleElement() {
    addQuery("{\"element\":\"value\"}");

    assertThat(CucumberRestTestContext.countEntries("$.element")).isEqualTo(1);
  }

  @Test
  void shouldGetZeroEntryForEmptyArray() {
    addQuery("{\"element\":[]}");

    assertThat(CucumberRestTestContext.countEntries("$.element")).isZero();
  }

  @Test
  void shouldGetArraySize() {
    addQuery("{\"element\":[{\"key\":\"value\"},{\"key\":\"value\"}]}");

    assertThat(CucumberRestTestContext.countEntries("$.element")).isEqualTo(2);
  }

  @Test
  void shouldNotReadStatusCodeForUnreadableStatusCode() throws IOException {
    ClientHttpResponse httpResponse = mock(ClientHttpResponse.class);
    when(httpResponse.getStatusCode()).thenThrow(IOException.class);

    assertThatThrownBy(() ->
      CucumberRestTestContext.addResponse(mockedRequest("/"), httpResponse, mock(ClientHttpRequestExecution.class), "body".getBytes(UTF_8))
    ).isExactlyInstanceOf(AssertionError.class);
  }

  @Test
  void shouldNotReadResponseCodeForUnreadableResponse() throws IOException {
    ClientHttpResponse httpResponse = mock(ClientHttpResponse.class);
    when(httpResponse.getStatusCode()).thenReturn(HttpStatus.OK);
    when(httpResponse.getBody()).thenThrow(IOException.class);

    CucumberRestTestContext.addResponse(mockedRequest("/"), httpResponse, mock(ClientHttpRequestExecution.class), "body".getBytes(UTF_8));

    assertThat(CucumberRestTestContext.getResponse()).isEmpty();
  }

  @Test
  void shouldGracefullyHandleRetryErrors() throws IOException {
    byte[] body = "body".getBytes(UTF_8);

    ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
    HttpRequest request = mockedRequest("/");
    when(execution.execute(request, body)).thenThrow(IOException.class);

    CucumberRestTestContext.addResponse(request, mockedResponse("response"), execution, body);

    assertThatThrownBy(CucumberRestTestContext::retry).isExactlyInstanceOf(AssertionError.class).hasMessageContaining("retrying");
  }

  private void addQuery(String response) {
    addQuery("/", response);
  }

  private void addQuery(String path, String response) {
    ClientHttpResponse httpResponse = mockedResponse(response);

    CucumberRestTestContext.addResponse(mockedRequest(path), httpResponse, mock(ClientHttpRequestExecution.class), "body".getBytes(UTF_8));
  }

  private ClientHttpResponse mockedResponse(String response) {
    ClientHttpResponse httpResponse = mock(ClientHttpResponse.class);

    assertDoesNotThrow(() -> {
      when(httpResponse.getStatusCode()).thenReturn(HttpStatus.OK);
      when(httpResponse.getBody()).thenReturn(new ByteArrayInputStream(response.getBytes(UTF_8)));
    });

    return httpResponse;
  }

  private HttpRequest mockedRequest(String path) {
    HttpRequest request = mock(HttpRequest.class);
    when(request.getURI()).thenReturn(URI.create("http://localhost" + path));
    return request;
  }
}
