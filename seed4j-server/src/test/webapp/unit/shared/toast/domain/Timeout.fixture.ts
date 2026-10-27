import { TimeoutListener, Timeoutable } from '@/shared/toast/domain/Timeout';
import { MockedFunction, vi } from 'vitest';

interface TimeoutListenerStub extends TimeoutListener {
  register: MockedFunction<(timeoutable: Timeoutable, duration: number) => void>;
  unregister: MockedFunction<() => void>;
}

export const stubTimeout = (): TimeoutListenerStub => ({
  register: vi.fn<(timeoutable: Timeoutable, duration: number) => void>(),
  unregister: vi.fn<() => void>(),
});
