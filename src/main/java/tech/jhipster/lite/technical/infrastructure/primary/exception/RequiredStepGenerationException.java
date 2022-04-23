package tech.jhipster.lite.technical.infrastructure.primary.exception;

import java.util.List;

public class RequiredStepGenerationException extends Exception {

  public RequiredStepGenerationException(String currentStep, List<String> requiredSteps) {
    super(getMessage(currentStep, requiredSteps));
  }

  private static String getMessage(String currentStep, List<String> requiredSteps) {
    String requiredStepsMsg = String.join(",", requiredSteps);
    return "Generation exception during " + currentStep + ": these steps should have been executed before (" + requiredStepsMsg + ")";
  }
}
