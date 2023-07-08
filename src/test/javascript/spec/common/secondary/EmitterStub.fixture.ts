import { Emitter } from 'mitt';
import sinon, { SinonStub } from 'sinon';

interface EmitterStub extends Emitter<any> {
  emit: SinonStub;
}

export const stubEmitter = (): EmitterStub =>
  ({
    emit: sinon.stub(),
  }) as EmitterStub;
