package tech.jhipster.lite.generator.project.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.zeroturnaround.zip.commons.FileUtilsV2_2.deleteDirectory;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.REMOTE_URL;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;

@UnitTest
@ExtendWith(SpringExtension.class)
class CloudRepositoryTest {

  @InjectMocks
  CloudRepository repository;

  @Test
  void shouldClone() throws IOException {
    Project project = tmpProjectWithRemote();
    File tmpTest = new File("/tmp/jhipster-lite");
    deleteDirectory(tmpTest);

    assertDoesNotThrow(() -> repository.clone(project));

    assertFileExist(project.getFolder());
  }

  @Test
  void shouldNotCloneWhenAlreadyCloned() throws IOException {
    Project project = tmpProjectWithRemote();

    repository.clone(project);
    assertDoesNotThrow(() -> repository.clone(project));
    assertFileExist(project.getFolder());
  }

  @Test
  void shouldNotCloneWhenCreateFolderFails() throws IOException {
    Project project = tmpProjectWithRemote();

    try (MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class);) {
      fileUtilsMock.when(() -> FileUtils.createFolder(any(String.class))).thenThrow(new IOException("error"));
      assertThatThrownBy(() -> repository.clone(project)).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldNotCloneWhenCloningFails() throws IOException {
    Project project = tmpProjectWithRemote();

    try (MockedStatic<Git> gitMock = Mockito.mockStatic(Git.class, RETURNS_DEEP_STUBS);) {
      gitMock
        .when(() ->
          Git
            .cloneRepository()
            .setURI(anyString())
            .setDirectory(any(File.class))
            .setCredentialsProvider(any(CredentialsProvider.class))
            .call()
        )
        .thenThrow(new InvalidRemoteException("Non existing repo"));
      assertThatThrownBy(() -> repository.clone(project)).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldNotCloneWhenNoRemote() {
    Project project = tmpProject();

    assertThatThrownBy(() -> repository.clone(project)).isExactlyInstanceOf(NoSuchElementException.class);
  }
}
