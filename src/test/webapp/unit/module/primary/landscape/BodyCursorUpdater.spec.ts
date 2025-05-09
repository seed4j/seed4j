import { describe, expect, it } from 'vitest';

import { BodyCursorUpdater } from '@/module/primary/landscape/BodyCursorUpdater';

import { stubWindow } from '../GlobalWindow.fixture';

describe('BodyCursorUpdater', () => {
  it('should set a cursor type', () => {
    const windowStub = stubWindow() as any;
    const service = new BodyCursorUpdater(windowStub);

    service.set('grabbing');

    expect(windowStub.document.body.style.cursor).toBe('grabbing');
  });

  it('should reset cursor', () => {
    const windowStub = stubWindow() as any;
    const service = new BodyCursorUpdater(windowStub);

    service.reset();

    expect(windowStub.document.body.style.cursor).toBe('auto');
  });
});
