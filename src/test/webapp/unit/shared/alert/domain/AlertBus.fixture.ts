import sinon, { SinonStub } from 'sinon';

import { AlertBus } from '@/shared/alert/domain/AlertBus';

export interface AlertBusFixture extends AlertBus {
  success: SinonStub;
  error: SinonStub;
}

export const stubAlertBus = (): AlertBusFixture => ({
  success: sinon.stub(),
  error: sinon.stub(),
});
