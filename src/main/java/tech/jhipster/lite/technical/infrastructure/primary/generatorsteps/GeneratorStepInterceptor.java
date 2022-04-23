package tech.jhipster.lite.technical.infrastructure.primary.generatorsteps;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.history.application.GeneratorHistoryApplicationService;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.RequiredGeneratorStep;
import tech.jhipster.lite.technical.infrastructure.primary.exception.RequiredStepGenerationException;

@Aspect
@Configuration
public class GeneratorStepInterceptor {

  private final GeneratorHistoryApplicationService generatorHistoryApplicationService;

  public GeneratorStepInterceptor(GeneratorHistoryApplicationService generatorHistoryApplicationService) {
    this.generatorHistoryApplicationService = generatorHistoryApplicationService;
  }

  @Around(value = "@annotation(generatorStep)")
  public void checkRequiredSteps(ProceedingJoinPoint joinPoint, GeneratorStep generatorStep) throws Throwable {
    String serviceId = generatorStep.id();
    List<String> requiredSteps = getRequiredSteps(joinPoint);

    ProjectDTO projectDTO = (ProjectDTO) joinPoint.getArgs()[0];
    Project project = ProjectDTO.toProject(projectDTO);

    List<String> missingSteps = new ArrayList<>();
    if (!requiredSteps.isEmpty()) {
      List<String> existingServiceIds = generatorHistoryApplicationService
        .getValues(project)
        .stream()
        .map(GeneratorHistoryValue::serviceId)
        .collect(Collectors.toList());

      missingSteps = requiredSteps.stream().filter(step -> !existingServiceIds.contains(step)).collect(Collectors.toList());
    }

    if (missingSteps.isEmpty()) {
      joinPoint.proceed();

      GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue(serviceId);
      generatorHistoryApplicationService.addHistoryValue(project, generatorHistoryValue);
    } else {
      throw new RequiredStepGenerationException(serviceId, missingSteps);
    }
  }

  private List<String> getRequiredSteps(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    RequiredGeneratorStep requiredGeneratorStepAnnotation = method.getAnnotation(RequiredGeneratorStep.class);
    if (requiredGeneratorStepAnnotation == null) {
      return new ArrayList<>();
    } else {
      return Arrays.asList(requiredGeneratorStepAnnotation.requiredSteps());
    }
  }
}
