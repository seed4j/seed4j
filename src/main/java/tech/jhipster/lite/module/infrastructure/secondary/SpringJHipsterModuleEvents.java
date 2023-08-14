package tech.jhipster.lite.module.infrastructure.secondary;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import tech.jhipster.lite.module.domain.JHipsterModuleApplied;
import tech.jhipster.lite.module.domain.JHipsterModuleEvents;
import tech.jhipster.lite.shared.error.domain.Assert;

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
