package com.seed4j.module.domain.nodejs;

import com.seed4j.shared.error.domain.GeneratorException;

public class UnknownNodePackageException extends GeneratorException {

  public UnknownNodePackageException(NodePackageName packageName, NodePackagesVersionSource source) {
    super(
      internalServerError(NodeErrorKey.UNKNOWN_PACKAGE)
        .message(buildMessage(packageName, source))
        .addParameter("packageName", packageName.get())
        .addParameter("packageSource", source.name())
    );
  }

  private static String buildMessage(NodePackageName packageName, NodePackagesVersionSource source) {
    return new StringBuilder()
      .append("Can't find ")
      .append(packageName.get())
      .append(" version in ")
      .append(source)
      .append(" package.json, forgot to add it?")
      .toString();
  }
}
