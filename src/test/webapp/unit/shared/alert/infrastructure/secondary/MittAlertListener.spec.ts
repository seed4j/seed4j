import { AlertType } from '@/shared/alert/infrastructure/secondary/AlertType';
import { MittAlertListener } from '@/shared/alert/infrastructure/secondary/MittAlertListener';
import mitt from 'mitt';
import { describe, expect, it, vi } from 'vitest';

describe('MittAlertListener', () => {
  it.each([
    ['onSuccess', AlertType.SUCCESS],
    ['onError', AlertType.ERROR],
  ])('should listen sent %s message', (method, alertType) => {
    const emitterStub = mitt();
    const mittAlertBus = new MittAlertListener(emitterStub);
    const spyAlerted = vi.fn();
    mittAlertBus[method as keyof MittAlertListener](spyAlerted);

    emitterStub.emit(alertType, 'A message');

    expect(spyAlerted).toHaveBeenCalledExactlyOnceWith('A message');
  });

  it.each([
    ['onSuccess', AlertType.SUCCESS],
    ['onError', AlertType.ERROR],
  ])('should unsubscribe %s', (method, alertType) => {
    const emitterStub = mitt();
    const mittAlertBus = new MittAlertListener(emitterStub);
    const spyAlerted = vi.fn();
    const unsubscribe = mittAlertBus[method as keyof MittAlertListener](spyAlerted);

    emitterStub.emit(alertType, 'A message');
    emitterStub.emit(alertType, 'A message');
    unsubscribe();
    emitterStub.emit(alertType, 'A message');
    emitterStub.emit(alertType, 'A message');

    expect(spyAlerted).toHaveBeenCalledTimes(2);
  });
});
