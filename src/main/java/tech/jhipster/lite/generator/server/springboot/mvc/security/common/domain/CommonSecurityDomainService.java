package tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_PATH;

import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record CommonSecurityDomainService(ProjectRepository projectRepository) implements CommonSecurityService {
  @Override
  public void updateExceptionTranslator(Project project) {
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String exceptionTranslatorPath = getPath(MAIN_JAVA, packageNamePath, "technical/infrastructure/primary/exception");
    String exceptionTranslatorFile = "ExceptionTranslator.java";

    String oldImport = "import org.zalando.problem.spring.web.advice.ProblemHandling;";
    String newImport =
      """
        import org.zalando.problem.spring.web.advice.ProblemHandling;
        import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;""";
    projectRepository.replaceText(project, exceptionTranslatorPath, exceptionTranslatorFile, oldImport, newImport);

    String oldImplements = "public class ExceptionTranslator implements ProblemHandling \\{";
    String newImplements = "public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait \\{";
    projectRepository.replaceText(project, exceptionTranslatorPath, exceptionTranslatorFile, oldImplements, newImplements);
  }

  @Override
  public void updateIntegrationTestWithMockUser(Project project) {
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String integrationTestPath = getPath(TEST_JAVA, packageNamePath);

    String oldImport = "import org.springframework.boot.test.context.SpringBootTest;";
    String newImport =
      """
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.security.test.context.support.WithMockUser;""";
    projectRepository.replaceText(project, integrationTestPath, "IntegrationTest.java", oldImport, newImport);

    String oldAnnotation = "public @interface";
    String newAnnotation = """
      @WithMockUser
      public @interface""";
    projectRepository.replaceText(project, integrationTestPath, "IntegrationTest.java", oldAnnotation, newAnnotation);
  }
}
