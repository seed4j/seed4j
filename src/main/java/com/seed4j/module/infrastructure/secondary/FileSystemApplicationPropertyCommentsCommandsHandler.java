package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.javaproperties.ApplicationPropertyComment;
import com.seed4j.module.domain.javaproperties.ApplicationPropertyComments;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

@Service
class FileSystemApplicationPropertyCommentsCommandsHandler {

  public void handle(Seed4JProjectFolder projectFolder, ApplicationPropertyComments comments) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("comments", comments);

    comments.get().forEach(setComment(projectFolder));
  }

  private Consumer<ApplicationPropertyComment> setComment(Seed4JProjectFolder projectFolder) {
    return comment ->
      ApplicationPropertiesFilePaths.existing(projectFolder, comment.file(), ".properties").ifPresent(path ->
        new PropertiesFileApplicationPropertyCommentsHandler(path).set(comment.key(), comment.value())
      );
  }
}
