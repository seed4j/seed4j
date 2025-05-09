import mitt from 'mitt';
import { describe, expect, it, vi } from 'vitest';

import { AlertType } from '@/shared/alert/infrastructure/secondary/AlertType';
import { MittAlertListener } from '@/shared/alert/infrastructure/secondary/MittAlertListener';

describe('MittAlertListener', () => {
  it('should listen sent success message', () => {
    const spyAlerted = vi.fn();
    const emitter = mitt();
    const mittAlertBus = new MittAlertListener(emitter);
    mittAlertBus.onSuccess(spyAlerted);

    emitter.emit(AlertType.SUCCESS, 'A message');

    expect(spyAlerted).toHaveBeenCalledExactlyOnceWith('A message');
  });

  it('should unsubscribe success', () => {
    const spyAlerted = vi.fn();
    const emitter = mitt();
    const mittAlertBus = new MittAlertListener(emitter);
    const unsubscribe = mittAlertBus.onSuccess(spyAlerted);

    emitter.emit(AlertType.SUCCESS, 'A message');
    emitter.emit(AlertType.SUCCESS, 'A message');
    unsubscribe();
    emitter.emit(AlertType.SUCCESS, 'A message');
    emitter.emit(AlertType.SUCCESS, 'A message');

    expect(spyAlerted).toHaveBeenCalledTimes(2);
  });

  it('should listen sent error message', () => {
    const spyAlerted = vi.fn();
    const emitter = mitt();
    const mittAlertBus = new MittAlertListener(emitter);
    mittAlertBus.onError(spyAlerted);

    emitter.emit(AlertType.ERROR, 'A message');

    expect(spyAlerted).toHaveBeenCalledExactlyOnceWith('A message');
  });

  it('should unsubscribe error', () => {
    const spyAlerted = vi.fn();
    const emitter = mitt();
    const mittAlertBus = new MittAlertListener(emitter);
    const unsubscribe = mittAlertBus.onError(spyAlerted);

    emitter.emit(AlertType.ERROR, 'A message');
    emitter.emit(AlertType.ERROR, 'A message');
    unsubscribe();
    emitter.emit(AlertType.ERROR, 'A message');
    emitter.emit(AlertType.ERROR, 'A message');

    expect(spyAlerted).toHaveBeenCalledTimes(2);
  });
});
