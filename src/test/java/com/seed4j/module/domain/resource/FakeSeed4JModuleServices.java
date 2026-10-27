package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.docker.DockerImageName;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImageVersions;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.docker.DockerVersion;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.nodejs.NodePackage;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.nodejs.NodePackagesVersionSource;
import com.seed4j.module.domain.nodejs.NodePackagesVersions;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Seed4JModuleServices} for module factories tests.
 *
 * <p>
 * Declare the versions needed by the tested factory with {@code dockerImages().put(...)} and
 * {@code nodeVersions().put(...)}. Node.js packages installations are recorded in
 * {@code nodeLazyPackagesInstaller().installations()}.
 * </p>
 */
public final class FakeSeed4JModuleServices implements Seed4JModuleServices {

  private final FakeDockerImages dockerImages = new FakeDockerImages();
  private final FakeNodeVersions nodeVersions = new FakeNodeVersions();
  private final FakeNodeLazyPackagesInstaller nodeLazyPackagesInstaller = new FakeNodeLazyPackagesInstaller();

  @Override
  public FakeDockerImages dockerImages() {
    return dockerImages;
  }

  @Override
  public FakeNodeVersions nodeVersions() {
    return nodeVersions;
  }

  @Override
  public FakeNodeLazyPackagesInstaller nodeLazyPackagesInstaller() {
    return nodeLazyPackagesInstaller;
  }

  public void reset() {
    dockerImages.reset();
    nodeVersions.reset();
    nodeLazyPackagesInstaller.reset();
  }

  public static final class FakeDockerImages implements DockerImages {

    private DockerImageVersions dockerImageVersions = DockerImageVersions.EMPTY;

    @Override
    public DockerImageVersions get() {
      return dockerImageVersions;
    }

    public void put(DockerImageVersion dockerImage) {
      this.dockerImageVersions = this.dockerImageVersions.merge(new DockerImageVersions(List.of(dockerImage)));
    }

    public void put(DockerImageName name, DockerVersion version) {
      put(new DockerImageVersion(name, version));
    }

    public void put(String name, String version) {
      put(new DockerImageVersion(name, version));
    }

    public void reset() {
      this.dockerImageVersions = DockerImageVersions.EMPTY;
    }
  }

  public static final class FakeNodeVersions implements NodeVersions {

    private NodePackagesVersions nodePackagesVersions = NodePackagesVersions.EMPTY;

    @Override
    public NodePackagesVersions get() {
      return nodePackagesVersions;
    }

    public void put(NodePackagesVersionSource source, NodePackage... packages) {
      this.nodePackagesVersions = this.nodePackagesVersions.merge(NodePackagesVersions.builder().put(source, List.of(packages)).build());
    }

    public void reset() {
      this.nodePackagesVersions = NodePackagesVersions.EMPTY;
    }
  }

  public static final class FakeNodeLazyPackagesInstaller implements NodeLazyPackagesInstaller {

    private final List<Installation> installations = new ArrayList<>();

    @Override
    public void runInstallIn(Seed4JProjectFolder folder, NodePackageManager nodePackageManager) {
      installations.add(new Installation(folder, nodePackageManager));
    }

    public List<Installation> installations() {
      return List.copyOf(installations);
    }

    public void reset() {
      installations.clear();
    }

    public record Installation(Seed4JProjectFolder folder, NodePackageManager nodePackageManager) {}
  }
}
