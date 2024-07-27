import sinon, { SinonStub } from 'sinon';
import { Logger } from '@/shared/logger/domain/Logger';

export interface LoggerFixture extends Logger {
  error: SinonStub;
}

export const stubLogger = (): LoggerFixture => ({
  error: sinon.stub(),
});
