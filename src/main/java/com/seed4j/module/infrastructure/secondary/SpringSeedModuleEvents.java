package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.SeedModuleApplied;
import com.seed4j.module.domain.SeedModuleEvents;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringSeedModuleEvents implements SeedModuleEvents {

  private final ApplicationEventPublisher events;

  public SpringSeedModuleEvents(ApplicationEventPublisher events) {
    this.events = events;
  }

  @Override
  public void dispatch(SeedModuleApplied moduleApplied) {
    Assert.notNull("moduleApplied", moduleApplied);

    events.publishEvent(moduleApplied);
  }
}
