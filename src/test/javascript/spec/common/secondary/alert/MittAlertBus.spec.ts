import sinon, { SinonStub } from 'sinon';
import { Emitter } from 'mitt';
import { MittAlertBus } from '@/common/secondary/alert/MittAlertBus';
import { AlertType } from '@/common/secondary/alert/AlertType';
import { describe, it, expect } from 'vitest';

interface EmitterStub extends Emitter<any> {
  emit: SinonStub;
}

const stubEmitter = (): EmitterStub =>
  ({
    emit: sinon.stub(),
  }) as EmitterStub;

describe('MittAlertBus', () => {
  it('should emit success', () => {
    const emitterStub = stubEmitter();
    const mittAlertBus = new MittAlertBus(emitterStub);

    mittAlertBus.success('A message');

    const [type, message] = emitterStub.emit.getCall(0).args;
    expect(type).toBe(AlertType.SUCCESS);
    expect(message).toBe('A message');
  });

  it('should emit error', () => {
    const emitterStub = stubEmitter();
    const mittAlertBus = new MittAlertBus(emitterStub);

    mittAlertBus.error('A message');

    const [type, message] = emitterStub.emit.getCall(0).args;
    expect(type).toBe(AlertType.ERROR);
    expect(message).toBe('A message');
  });
});
