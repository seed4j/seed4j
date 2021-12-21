package tech.jhipster.lite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class JHLiteApp {

  private static final Logger log = LoggerFactory.getLogger(JHLiteApp.class);

  private static String hello = "hello world";

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(JHLiteApp.class);
    Environment env = app.run(args).getEnvironment();
    logApplicationStartup(env);
  }

  private static void logApplicationStartup(Environment env) {
    String protocol = getProtocol(env.getProperty("server.ssl.key-store"));
    String serverPort = env.getProperty("server.port");
    String contextPath = getContextPath(env.getProperty("server.servlet.context-path"));
    String hostAddress = getHostAddress();
    log.info(
      """

      ----------------------------------------------------------
        Application '{}' is running! Access URLs:
        Local: \t{}://localhost:{}{}swagger-ui.html
        External: \t{}://{}:{}{}swagger-ui.html
        Profile(s): \t{}
      ----------------------------------------------------------
      """,
      env.getProperty("spring.application.name"),
      protocol,
      serverPort,
      contextPath,
      protocol,
      hostAddress,
      serverPort,
      contextPath,
      env.getActiveProfiles()
    );
  }

  public static String getProtocol(String value) {
    return Optional.ofNullable(value).map(key -> "https").orElse("http");
  }

  public static String getContextPath(String value) {
    return Optional.ofNullable(value).filter(StringUtils::isNotBlank).orElse("/");
  }

  public static String getHostAddress() {
    String hostAddress = "localhost";
    try {
      hostAddress = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      log.warn("The host name could not be determined, using `localhost` as fallback");
    }
    return hostAddress;
  }
}
