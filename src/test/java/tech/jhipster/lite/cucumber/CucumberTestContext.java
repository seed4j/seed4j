package tech.jhipster.lite.cucumber;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.spi.json.JsonProvider;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Function;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public final class CucumberTestContext {

  private static final Deque<RestQuery> queries = new ConcurrentLinkedDeque<>();
  private static JsonProvider jsonReader = Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS).jsonProvider();

  private CucumberTestContext() {}

  public static void addResponse(HttpRequest request, ClientHttpResponse response, ClientHttpRequestExecution execution, byte[] body) {
    queries.addFirst(new RestQuery(request, response, execution, body));
  }

  public static Object getElement(String jsonPath) {
    return lastQuery().response().map(toElement(jsonPath)).orElse(null);
  }

  public static Object getElement(String uri, String jsonPath) {
    return queries
      .stream()
      .filter(query -> query.response.isPresent() && StringUtils.isNotBlank(query.response.get()))
      .filter(query -> query.forUri(uri))
      .findFirst()
      .flatMap(response -> response.response.map(toElement(jsonPath)))
      .orElse(null);
  }

  public static List<String> getResponseHeader(String header) {
    return Collections.unmodifiableList(lastQuery().responseHeaders().get(header));
  }

  public static int countEntries(String jsonPath) {
    return lastQuery().response().map(toEntriesCount(jsonPath)).orElse(0);
  }

  private static Function<String, Integer> toEntriesCount(String jsonPath) {
    return response -> {
      Object element = null;
      try {
        element = JsonPath.read(jsonReader.parse(response), jsonPath);
      } catch (PathNotFoundException e) {
        return 0;
      }

      if (!(element instanceof JSONArray)) {
        return 1;
      }

      JSONArray array = (JSONArray) element;
      if (array.isEmpty()) {
        return 0;
      }

      return array.size();
    };
  }

  private static Function<String, Object> toElement(String jsonPath) {
    return response -> {
      try {
        Object element = JsonPath.read(jsonReader.parse(response), jsonPath);

        return element;
      } catch (PathNotFoundException e) {
        return null;
      }
    };
  }

  public static HttpStatus getStatus() {
    return lastQuery().status();
  }

  public static String getUri() {
    return lastQuery().uri();
  }

  public static Optional<String> getResponse() {
    return lastQuery().response();
  }

  public static void reset() {
    queries.clear();
  }

  public static void retry() {
    lastQuery().retry();
  }

  private static RestQuery lastQuery() {
    try {
      return queries.getFirst();
    } catch (NoSuchElementException e) {
      throw new AssertionError("Can't get last query: empty queries");
    }
  }

  private static class RestQuery {

    private static final String URI_MATCHER = ".*\\/%s(\\/[\\w-]*\\/?)?";

    private final HttpRequest request;
    private final String uri;
    private final HttpStatus status;
    private final Optional<String> response;
    private final HttpHeaders responseHeaders;
    private final ClientHttpRequestExecution execution;
    private final byte[] body;

    public RestQuery(HttpRequest request, ClientHttpResponse response, ClientHttpRequestExecution execution, byte[] body) {
      this.request = request;
      try {
        uri = URLDecoder.decode(request.getURI().toString(), StandardCharsets.UTF_8.displayName());
        responseHeaders = response.getHeaders();
        status = response.getStatusCode();
      } catch (IOException e) {
        throw new AssertionError(e.getMessage(), e);
      }

      this.response = readResponse(response);
      this.execution = execution;
      this.body = body;
    }

    private Optional<String> readResponse(ClientHttpResponse response) {
      try {
        return Optional.of(StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
      } catch (Exception e) {
        return Optional.empty();
      }
    }

    public String uri() {
      return uri;
    }

    private HttpStatus status() {
      return status;
    }

    private Optional<String> response() {
      return response;
    }

    private HttpHeaders responseHeaders() {
      return responseHeaders;
    }

    /**
     * Matches the supplied URI respecting REST principles.
     *
     * <pre>
     * true  = "/api/working-folders".forUri("working-folders")
     * true  = "/api/working-folders/".forUri("working-folders")
     * true  = "/api/working-folders/e47df162-f397".forUri("working-folders")
     * true  = "/api/working-folders/e47df162-f397/".forUri("working-folders")
     * false = "/api/working-folders/e47df162-f397/commentaries".forUri("working-folders")
     * false = "/api/working-folders/e47df162-f397/commentaries/".forUri("working-folders")
     * false = "/api/working-folders/e47df162-f397/commentaries/955eea5e-9fbf".forUri("working-folders")
     * false = "/api/working-folders/e47df162-f397/commentaries/955eea5e-9fbf/".forUri("working-folders")
     * </pre>
     *
     * @param uri
     *          name of a REST resource, such as "working-folders"
     */
    private boolean forUri(String uri) {
      if (!uri.matches("[\\w-]+")) Assertions.fail("URI should be the name of a REST resource");

      return this.uri.matches(String.format(URI_MATCHER, uri));
    }

    private void retry() {
      try {
        ClientHttpResponse response = execution.execute(request, body);

        CucumberTestContext.addResponse(request, response, execution, body);
      } catch (IOException e) {
        throw new AssertionError("Error while retrying last call: " + e.getMessage(), e);
      }
    }
  }
}
