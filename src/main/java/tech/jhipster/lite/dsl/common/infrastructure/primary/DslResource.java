package tech.jhipster.lite.dsl.common.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.Objects;
import org.springframework.http.MediaType;
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
  private final ProjectFolder projectFolder;

  public DslResource(JHipsterDslApplicationService dslApplicationService, ProjectFolder projectFolder) {
    this.dslApplicationService = dslApplicationService;
    this.projectFolder = projectFolder;
  }

  @PostMapping(value = "apply-dsl", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
  public void applyDsl(@RequestPart String properties, @RequestParam MultipartFile file) {
    JhipsterDslFileToImport.DslFileImportBuilder builder = JhipsterDslFileToImport.builder();
    RestJHipsterDslProperties dslProperties = null;
    try {
      dslProperties = new ObjectMapper().readValue(properties, RestJHipsterDslProperties.class);

      JhipsterDslFileToImport importDslFile = builder
        .originalFilename(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())))
        .contentType(file.getContentType())
        .name(file.getName())
        .bytes(file.getBytes())
        .build();

      dslApplicationService.importAndGenerate(importDslFile, dslProperties.toDomain(projectFolder));
    } catch (IOException e) {
      throw new InvalidDslFileException("Error when read file upload", e);
    }
  }
}
