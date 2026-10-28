package com.seed4j.statistic.infrastructure.primary;

import com.seed4j.module.domain.Seed4JModuleApplied;
import com.seed4j.statistic.application.StatisticsApplicationService;
import com.seed4j.statistic.domain.AppliedModule;
import com.seed4j.statistic.domain.AppliedModuleId;
import com.seed4j.statistic.domain.Module;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

@Component
class SpringSeed4JModuleEventListener implements ApplicationListener<PayloadApplicationEvent<Seed4JModuleApplied>> {

  private final StatisticsApplicationService statistics;

  public SpringSeed4JModuleEventListener(StatisticsApplicationService statistics) {
    this.statistics = statistics;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<Seed4JModuleApplied> event) {
    statistics.moduleApplied(toModuleApplied(event.getPayload()));
  }

  private AppliedModule toModuleApplied(Seed4JModuleApplied moduleApplied) {
    return AppliedModule.builder().id(AppliedModuleId.newId()).module(new Module(moduleApplied.slug().get())).date(moduleApplied.time());
  }
}
