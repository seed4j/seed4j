import { AlertListener } from '@/shared/alert/domain/AlertListener';
import sinon, { SinonStub } from 'sinon';

export interface AlertListenerFixture extends AlertListener {
  onSuccess: SinonStub;
  onError: SinonStub;
}

export const stubAlertListener = (): AlertListenerFixture => ({
  onSuccess: sinon.stub(),
  onError: sinon.stub(),
});
