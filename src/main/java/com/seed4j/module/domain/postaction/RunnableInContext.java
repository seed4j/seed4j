package com.seed4j.module.domain.postaction;

@FunctionalInterface
public interface RunnableInContext {
  void run(Seed4JModuleExecutionContext context);
}
