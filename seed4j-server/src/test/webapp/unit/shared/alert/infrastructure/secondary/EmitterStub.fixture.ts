import { Emitter } from 'mitt';
import { type MockedFunction, vi } from 'vitest';

interface EmitterStub extends Emitter<any> {
  emit: MockedFunction<Emitter<any>['emit']>;
}

export const stubEmitter = (): EmitterStub =>
  ({
    emit: vi.fn(),
  }) as EmitterStub;
