package tech.jhipster.lite.dsl.common.infrastructure.primary;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.lite.dsl.common.application.JHipsterDslApplicationService;
import tech.jhipster.lite.dsl.parser.domain.JhipsterDslFileToImport;
import tech.jhipster.lite.projectfolder.domain.ProjectFolder;

@RestController
@Tag(name = "Dsl")
@RequestMapping("/api")
class DslResource {

  private final JHipsterDslApplicationService dslApplicationService;

  public DslResource(JHipsterDslApplicationService dslApplicationService, ProjectFolder projectFolder) {
    this.dslApplicationService = dslApplicationService;
  }

  @Hidden
  @PostMapping("apply-dsl")
  public void applyDsl(@RequestPart("file") MultipartFile file) {
    JhipsterDslFileToImport.DslFileImportBuilder builder = JhipsterDslFileToImport.builder();

    try {
      JhipsterDslFileToImport importDslFile = builder
        .originalFilename(StringUtils.cleanPath(file.getOriginalFilename()))
        .contentType(file.getContentType())
        .name(file.getName())
        .bytes(file.getBytes())
        .build();

      dslApplicationService.importAndGenerate(importDslFile);
    } catch (IOException e) {
      throw new InvalidDslFileException("Error when read file upload", e);
    }
  }
}
