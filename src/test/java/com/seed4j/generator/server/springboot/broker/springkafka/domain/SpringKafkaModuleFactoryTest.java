package com.seed4j.generator.server.springboot.broker.springkafka.domain;

import static com.seed4j.module.domain.Seed4JModulesFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImages;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SpringKafkaModuleFactoryTest {

  private static final SpringKafkaModuleFactory factory = new SpringKafkaModuleFactory(stubDockerImages());

  @Test
  void shouldBuildInit() {
    Seed4JModule module = factory.buildModuleInit(defaultModuleProperties());

    assertThat(module).isNotNull();
    assertThat(module.files()).isNotEmpty();
  }

  @Test
  void shouldBuildSampleProducerConsumer() {
    Seed4JModule module = factory.buildModuleSampleProducerConsumer(defaultModuleProperties());

    assertThat(module).isNotNull();
    assertThat(module.files()).isNotEmpty();
  }

  @Test
  void shouldBuildAkhq() {
    Seed4JModule module = factory.buildModuleAkhq(defaultModuleProperties());

    assertThat(module).isNotNull();
    assertThat(module.files()).isNotEmpty();
  }

  private static DockerImages stubDockerImages() {
    Map<String, DockerImageVersion> images = new HashMap<>();
    images.put("apache/kafka-native", new DockerImageVersion("apache/kafka-native", "4.0.2"));
    images.put("tchiotludo/akhq", new DockerImageVersion("tchiotludo/akhq", "latest"));
    return new DockerImages(images);
  }
}
