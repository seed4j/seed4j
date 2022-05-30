package tech.jhipster.lite.generator.project.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.zeroturnaround.zip.commons.FileUtilsV2_2.deleteDirectory;
import static tech.jhipster.lite.TestUtils.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GitHub;
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

  String token;

  @InjectMocks
  CloudRepository repository;

  @BeforeEach
  void createFakes() {
    token = "FakeToken";
  }

  @Test
  void shouldClone() throws IOException {
    Project project = tmpProjectWithRemote();
    File tmpTest = new File("/tmp/jhipster-lite");
    deleteDirectory(tmpTest);

    assertDoesNotThrow(() -> repository.clone(project, token));

    assertFileExist(project.getFolder());
  }

  @Test
  void shouldNotCloneWhenAlreadyCloned() throws IOException {
    Project project = tmpProjectWithRemote();

    repository.clone(project, token);
    assertDoesNotThrow(() -> repository.clone(project, token));
    assertFileExist(project.getFolder());
  }

  @Test
  void shouldNotCloneWhenCreateFolderFails() throws IOException {
    Project project = tmpProjectWithRemote();

    try (MockedStatic<FileUtils> fileUtilsMock = Mockito.mockStatic(FileUtils.class);) {
      fileUtilsMock.when(() -> FileUtils.createFolder(any(String.class))).thenThrow(new IOException("error"));
      assertThatThrownBy(() -> repository.clone(project, token)).isExactlyInstanceOf(GeneratorException.class);
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
      assertThatThrownBy(() -> repository.clone(project, token)).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldNotCloneWhenNoRemote() {
    Project project = tmpProject();

    assertThatThrownBy(() -> repository.clone(project, token)).isExactlyInstanceOf(NoSuchElementException.class);
  }

  @Test
  void shouldCreatePullRequest() {
    Project project = tmpProjectWithRemote();

    //TODO
    try (MockedStatic<GitUtils> gitUtilsMock = Mockito.mockStatic(GitUtils.class)) {
      gitUtilsMock.when(() -> GitUtils.connectionWithOauth(anyString())).then();
      gitUtilsMock.when(() -> GitUtils.addAndCommit(anyString(), anyString())).then();
      gitUtilsMock.when(() -> GitUtils.push(anyString(), anyString())).then();
      gitUtilsMock.when(() -> GitUtils.createPR(any(GitHub.class), anyString(), anyString(), anyString(), anyString(), anyString())).then();

      repository.pullRequest(project, token);
    }
  }

  @Test
  void shouldNotCreatePullRequestIfNoRemoteURL() {
    Project project = tmpProject();

    assertThatThrownBy(() -> repository.pullRequest(project, token)).isExactlyInstanceOf(NoSuchElementException.class);
  }

  @Test
  void shouldNotCreatePullRequestIfWrongFolder() {
    Project project = tmpProjectWithRemote();

    assertThatThrownBy(() -> repository.pullRequest(project, token))
      .isExactlyInstanceOf(GeneratorException.class)
      .hasMessage("This folder is not a git repository. Please provide the same position than your cloned project");
  }

  @Test
  void shouldNotCreatePullRequestIfWrongCredentials() {
    Project project = tmpProjectWithRemote();
    repository.clone(project, token);
    try (MockedStatic<GitHub> gitHubMock = Mockito.mockStatic(GitHub.class)) {
      gitHubMock.when(() -> GitHub.connectUsingOAuth(anyString())).thenThrow(new IOException());
      assertThatThrownBy(() -> repository.pullRequest(project, token))
        .isExactlyInstanceOf(GeneratorException.class)
        .hasMessage("Error when connecting with Oauth Token");
    }
  }
}
