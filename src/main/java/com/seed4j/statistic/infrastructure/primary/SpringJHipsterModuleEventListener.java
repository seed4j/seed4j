package com.seed4j.statistic.infrastructure.primary;

import com.seed4j.module.domain.SeedModuleApplied;
import com.seed4j.statistic.application.StatisticsApplicationService;
import com.seed4j.statistic.domain.AppliedModule;
import com.seed4j.statistic.domain.AppliedModuleId;
import com.seed4j.statistic.domain.Module;
import com.seed4j.statistic.domain.ModuleProperties;
import com.seed4j.statistic.domain.ProjectPath;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

@Component
class SpringJHipsterModuleEventListener implements ApplicationListener<PayloadApplicationEvent<SeedModuleApplied>> {

  private final StatisticsApplicationService statistics;

  public SpringJHipsterModuleEventListener(StatisticsApplicationService statistics) {
    this.statistics = statistics;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<SeedModuleApplied> event) {
    statistics.moduleApplied(toModuleApplied(event.getPayload()));
  }

  private AppliedModule toModuleApplied(SeedModuleApplied moduleApplied) {
    return AppliedModule.builder()
      .id(AppliedModuleId.newId())
      .path(new ProjectPath(moduleApplied.properties().projectFolder().get()))
      .module(new Module(moduleApplied.slug().get()))
      .date(moduleApplied.time())
      .properties(new ModuleProperties(moduleApplied.properties().getParameters()));
  }
}
