package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.Seed4JModuleApplied;
import com.seed4j.module.domain.Seed4JModuleEvents;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringSeed4JModuleEvents implements Seed4JModuleEvents {

  private final ApplicationEventPublisher events;

  public SpringSeed4JModuleEvents(ApplicationEventPublisher events) {
    this.events = events;
  }

  @Override
  public void dispatch(Seed4JModuleApplied moduleApplied) {
    Assert.notNull("moduleApplied", moduleApplied);

    events.publishEvent(moduleApplied);
  }
}
