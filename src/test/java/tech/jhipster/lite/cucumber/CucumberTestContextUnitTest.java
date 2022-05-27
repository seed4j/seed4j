package tech.jhipster.lite.cucumber;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import tech.jhipster.lite.UnitTest;

@UnitTest
class CucumberTestContextUnitTest {

  @Test
  void shouldGetQueryInformation() {
    addQuery("{}");

    assertThat(CucumberTestContext.getStatus()).isEqualTo(HttpStatus.OK);
    assertThat(CucumberTestContext.getUri()).isEqualTo("http://localhost/");
    assertThat(CucumberTestContext.getResponse()).contains("{}");
  }

  @Test
  void shouldResetTestContext() {
    addQuery("{}");

    CucumberTestContext.reset();

    assertThatThrownBy(() -> CucumberTestContext.getResponse()).isExactlyInstanceOf(AssertionError.class).hasMessageContaining("empty");
  }

  @Test
  void shouldGetZeroEntriesForUnknownPath() {
    addQuery("{}");

    assertThat(CucumberTestContext.countEntries("$.dummy")).isZero();
  }

  @Test
  void shouldGetNulForUnknownElement() {
    addQuery("{\"element\":\"value\"}");

    assertThat(CucumberTestContext.getElement("dummy")).isNull();
  }

  @Test
  void shouldGetElement() {
    addQuery("{\"element\":\"value\"}");

    assertThat(CucumberTestContext.getElement("element")).isEqualTo("value");
  }

  @Test
  void shouldGetElementWithPath() {
    addQuery("/dummies", "{\"element\":\"old\"}");
    addQuery("/dummies", "{\"element\":\"value\"}");
    addQuery("/dumm", "{\"element\":\"dd\"}");
    addQuery("/employees", "{\"element\":\"test\"}");

    assertThat(CucumberTestContext.getElement("dummies", "element")).isEqualTo("value");
  }

  @Test
  void shouldGetOneEntryForSingleElement() {
    addQuery("{\"element\":\"value\"}");

    assertThat(CucumberTestContext.countEntries("$.element")).isEqualTo(1);
  }

  @Test
  void shouldGetZeroEntryForEmptyArray() {
    addQuery("{\"element\":[]}");

    assertThat(CucumberTestContext.countEntries("$.element")).isZero();
  }

  @Test
  void shouldGetArraySize() {
    addQuery("{\"element\":[{\"key\":\"value\"},{\"key\":\"value\"}]}");

    assertThat(CucumberTestContext.countEntries("$.element")).isEqualTo(2);
  }

  @Test
  void shouldNotReadStatusCodeForUreadableStatusCode() throws UnsupportedEncodingException {
    ClientHttpResponse httpResponse = mock(ClientHttpResponse.class);
    try {
      when(httpResponse.getStatusCode()).thenThrow(IOException.class);
    } catch (IOException e) {
      fail(e.getMessage());
    }

    assertThatThrownBy(() ->
        CucumberTestContext.addResponse(mockedRequest("/"), httpResponse, mock(ClientHttpRequestExecution.class), "body".getBytes())
      )
      .isExactlyInstanceOf(AssertionError.class);
  }

  @Test
  void shouldNotReadResponseCodeForUreadableResponse() throws UnsupportedEncodingException {
    ClientHttpResponse httpResponse = mock(ClientHttpResponse.class);
    try {
      when(httpResponse.getStatusCode()).thenReturn(HttpStatus.OK);
      when(httpResponse.getBody()).thenThrow(IOException.class);
    } catch (IOException e) {
      fail(e.getMessage());
    }

    CucumberTestContext.addResponse(mockedRequest("/"), httpResponse, mock(ClientHttpRequestExecution.class), "body".getBytes());

    assertThat(CucumberTestContext.getResponse()).isEmpty();
  }

  @Test
  void shouldGracefullyHandleRetryErrors() throws IOException {
    byte[] body = "body".getBytes();

    ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
    HttpRequest request = mockedRequest("/");
    when(execution.execute(request, body)).thenThrow(IOException.class);

    CucumberTestContext.addResponse(request, mockedResponse("response"), execution, body);

    assertThatThrownBy(() -> CucumberTestContext.retry()).isExactlyInstanceOf(AssertionError.class).hasMessageContaining("retrying");
  }

  private void addQuery(String response) {
    addQuery("/", response);
  }

  private void addQuery(String path, String response) {
    ClientHttpResponse httpResponse = mockedResponse(response);

    CucumberTestContext.addResponse(mockedRequest(path), httpResponse, mock(ClientHttpRequestExecution.class), "body".getBytes());
  }

  private ClientHttpResponse mockedResponse(String response) {
    ClientHttpResponse httpResponse = mock(ClientHttpResponse.class);

    try {
      when(httpResponse.getStatusCode()).thenReturn(HttpStatus.OK);
      when(httpResponse.getBody()).thenReturn(new ByteArrayInputStream(response.getBytes()));
    } catch (IOException e) {
      fail(e.getMessage());
    }

    return httpResponse;
  }

  private HttpRequest mockedRequest(String path) {
    HttpRequest request = mock(HttpRequest.class);
    try {
      when(request.getURI()).thenReturn(new URI("http://localhost" + path));
    } catch (URISyntaxException e) {
      fail(e.getMessage());
    }
    return request;
  }
}
