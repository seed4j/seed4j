package tech.jhipster.lite.generator.tools.infrastructure.secondary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class GitUtils {

  public static final String USERNAME = "JHipster Bot";
  public static final String EMAIL = "jhipster-bot@jhipster.tech";

  private GitUtils() {}

  public static Git init(String dir) throws GitAPIException {
    return Git.init().setDirectory(new File(dir)).call();
  }

  public static void addAndCommit(String dir, String message) throws GitAPIException, IOException {
    add(dir);
    commit(dir, message);
  }

  public static void add(String dir) throws GitAPIException, IOException {
    Git git = getGit(dir);
    git.add().addFilepattern(".").call();
  }

  public static void commit(String dir, String message) throws GitAPIException, IOException {
    Git git = getGit(dir);
    git.commit().setCommitter(USERNAME, EMAIL).setMessage(message).setSign(false).call();
  }

  public static void apply(String dir, String patch) throws GitAPIException, IOException {
    Git git = getGit(dir);
    FileInputStream fileInputStream = new FileInputStream(patch);
    git.apply().setPatch(fileInputStream).call();
  }

  public static Git getGit(String dir) throws IOException {
    return Git.open(new File(dir));
  }
}
