package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

@UnitTest
class MongodbTest {

  @Test
  void shouldGetDockerImageName() {
    assertThat(Mongodb.getMongodbDockerImage()).isEqualTo("mongo:4.4.11");
  }

  @Test
  void shouldGetMongodbDockerVersion() {
    assertThat(Mongodb.getMongodbDockerVersion()).isEqualTo(Mongodb.MONGODB_DOCKER_VERSION);
  }

  @Test
  void shouldGetMongodbDockerImage() {
    assertThat(Mongodb.getMongodbDockerImage()).isEqualTo(Mongodb.MONGODB_DOCKER_IMAGE);
  }

  @Test
  void shouldMongodbDriver() {
    Dependency dependency = Mongodb.mongodbDriver();

    assertThat(dependency.getGroupId()).isEqualTo("org.mongodb");
    assertThat(dependency.getArtifactId()).isEqualTo("mongodb-driver-sync");
  }
}
