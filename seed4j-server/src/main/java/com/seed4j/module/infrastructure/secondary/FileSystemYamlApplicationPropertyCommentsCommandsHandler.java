package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.javaproperties.ApplicationPropertyComment;
import com.seed4j.module.domain.javaproperties.ApplicationPropertyComments;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

@Service
class FileSystemYamlApplicationPropertyCommentsCommandsHandler {

  public void handle(Indentation indentation, Seed4JProjectFolder projectFolder, ApplicationPropertyComments comments) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("comments", comments);

    comments.get().forEach(setComment(indentation, projectFolder));
  }

  private Consumer<ApplicationPropertyComment> setComment(Indentation indentation, Seed4JProjectFolder projectFolder) {
    return comment ->
      ApplicationPropertiesFilePaths.existing(projectFolder, comment.file(), ".yml").ifPresent(path ->
        new YamlFileApplicationPropertiesHandler(path, indentation).setComment(comment.key(), comment.value())
      );
  }
}
