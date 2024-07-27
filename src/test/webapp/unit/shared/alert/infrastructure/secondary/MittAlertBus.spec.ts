import { Emitter } from 'mitt';
import { MittAlertBus } from '@/shared/alert/infrastructure/secondary/MittAlertBus';
import { AlertType } from '@/shared/alert/infrastructure/secondary/AlertType';
import { describe, expect, it, vi } from 'vitest';

interface EmitterStub extends Emitter<any> {
  emit: vi.fn;
}

const stubEmitter = (): EmitterStub =>
  ({
    emit: vi.fn(),
  }) as EmitterStub;

describe('MittAlertBus', () => {
  it('should emit success', () => {
    const emitterStub = stubEmitter();
    const mittAlertBus = new MittAlertBus(emitterStub);

    mittAlertBus.success('A message');

    expect(emitterStub.emit).toHaveBeenCalledTimes(1);
    expect(emitterStub.emit).toBeCalledWith(AlertType.SUCCESS, 'A message');
  });

  it('should emit error', () => {
    const emitterStub = stubEmitter();
    const mittAlertBus = new MittAlertBus(emitterStub);

    mittAlertBus.error('A message');

    expect(emitterStub.emit).toHaveBeenCalledTimes(1);
    expect(emitterStub.emit).toBeCalledWith(AlertType.ERROR, 'A message');
  });
});
