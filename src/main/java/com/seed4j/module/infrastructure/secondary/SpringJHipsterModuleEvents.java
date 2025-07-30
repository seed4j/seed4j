package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.JHipsterModuleApplied;
import com.seed4j.module.domain.JHipsterModuleEvents;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringJHipsterModuleEvents implements JHipsterModuleEvents {

  private final ApplicationEventPublisher events;

  public SpringJHipsterModuleEvents(ApplicationEventPublisher events) {
    this.events = events;
  }

  @Override
  public void dispatch(JHipsterModuleApplied moduleApplied) {
    Assert.notNull("moduleApplied", moduleApplied);

    events.publishEvent(moduleApplied);
  }
}
