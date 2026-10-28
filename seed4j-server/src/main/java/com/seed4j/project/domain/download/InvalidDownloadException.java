package com.seed4j.project.domain.download;

import com.seed4j.shared.error.domain.GeneratorException;

class InvalidDownloadException extends GeneratorException {

  public InvalidDownloadException() {
    super(badRequest(DownloadErrorKey.INVALID_DOWNLOAD).message("A user tried to download a project from a protected folder"));
  }
}
