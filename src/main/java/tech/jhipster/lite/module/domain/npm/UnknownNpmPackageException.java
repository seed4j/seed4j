package tech.jhipster.lite.module.domain.npm;

import tech.jhipster.lite.shared.error.domain.GeneratorException;

public class UnknownNpmPackageException extends GeneratorException {

  public UnknownNpmPackageException(NpmPackageName packageName, NpmVersionSource source) {
    super(
      internalServerError(NpmErrorKey.UNKNOWN_PACKAGE)
        .message(buildMessage(packageName, source))
        .addParameter("packageName", packageName.get())
        .addParameter("packageSource", source.name())
    );
  }

  private static String buildMessage(NpmPackageName packageName, NpmVersionSource source) {
    return new StringBuilder()
      .append("Can't find ")
      .append(packageName.get())
      .append(" version in ")
      .append(source)
      .append(" package.json, forgot to add it?")
      .toString();
  }
}
