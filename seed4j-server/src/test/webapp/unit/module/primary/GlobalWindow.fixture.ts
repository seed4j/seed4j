import { WindowAction } from '@/WindowAction';
import { vi } from 'vitest';

export const stubWindow = (query?: string): WindowAction => ({
  URL: { createObjectURL: vi.fn(), revokeObjectURL: vi.fn() },
  document: {
    createElement: vi.fn(),
    body: { style: { cursor: undefined }, appendChild: vi.fn(), removeChild: vi.fn() },
    documentElement: { className: undefined },
  },
  matchMedia: () => ({ matches: query === '(prefers-color-scheme: dark)' }),
});
