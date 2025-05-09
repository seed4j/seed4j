import { describe, expect, it } from 'vitest';

import { AlertType } from '@/shared/alert/infrastructure/secondary/AlertType';
import { MittAlertBus } from '@/shared/alert/infrastructure/secondary/MittAlertBus';

import { stubEmitter } from './EmitterStub.fixture';

describe('MittAlertBus', () => {
  it('should emit success', () => {
    const emitterStub = stubEmitter();
    const mittAlertBus = new MittAlertBus(emitterStub);

    mittAlertBus.success('A message');

    expect(emitterStub.emit).toHaveBeenCalledExactlyOnceWith(AlertType.SUCCESS, 'A message');
  });

  it('should emit error', () => {
    const emitterStub = stubEmitter();
    const mittAlertBus = new MittAlertBus(emitterStub);

    mittAlertBus.error('A message');

    expect(emitterStub.emit).toHaveBeenCalledExactlyOnceWith(AlertType.ERROR, 'A message');
  });
});
