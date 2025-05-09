import sinon, { SinonStub } from 'sinon';

import { Timeoutable, TimeoutListener } from '@/shared/toast/domain/Timeout';

interface TimeoutListenerStub extends TimeoutListener {
  register: SinonStub<[Timeoutable, number]>;
  unregister: SinonStub;
}

export const stubTimeout = (): TimeoutListenerStub => ({
  register: sinon.stub<[Timeoutable, number]>(),
  unregister: sinon.stub(),
});
