import { AlertBus } from '@/shared/alert/domain/AlertBus';
import { type MockedFunction, vi } from 'vitest';

export interface AlertBusFixture extends AlertBus {
  success: MockedFunction<any>;
  error: MockedFunction<any>;
}

export const stubAlertBus = (): AlertBusFixture => ({
  success: vi.fn(),
  error: vi.fn(),
});
