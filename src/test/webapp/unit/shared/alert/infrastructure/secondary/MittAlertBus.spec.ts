import { AlertType } from '@/shared/alert/infrastructure/secondary/AlertType';
import { MittAlertBus } from '@/shared/alert/infrastructure/secondary/MittAlertBus';
import { describe, expect, it } from 'vitest';
import { stubEmitter } from './EmitterStub.fixture';

describe('MittAlertBus', () => {
  it.each([
    ['success', AlertType.SUCCESS],
    ['error', AlertType.ERROR],
  ])('should emit %s', (method, alertType) => {
    const emitterStub = stubEmitter();
    const mittAlertBus = new MittAlertBus(emitterStub);

    mittAlertBus[method as keyof MittAlertBus]('A message');

    expect(emitterStub.emit).toHaveBeenCalledExactlyOnceWith(alertType, 'A message');
  });
});
