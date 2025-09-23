import { BodyCursorUpdater } from '@/module/primary/landscape/BodyCursorUpdater';
import { ApplicationListener } from '@/shared/alert/infrastructure/primary/ApplicationListener';
import { WindowApplicationListener } from '@/shared/alert/infrastructure/primary/WindowApplicationListener';
import { WindowAction } from '@/WindowAction';
import { key, piqureWrapper } from 'piqure';

const { provide, inject } = piqureWrapper(globalThis as unknown as Window, 'piqure');

export { inject, provide };

export const GLOBAL_WINDOW = key<WindowAction>('globalWindow');
export const APPLICATION_LISTENER = key<ApplicationListener>('applicationListener');
export const CURSOR_UPDATER = key<BodyCursorUpdater>('cursorUpdater');

export const provideWindowsTooling = (): void => {
  provide(GLOBAL_WINDOW, window);
  provide(APPLICATION_LISTENER, new WindowApplicationListener(globalThis as unknown as Window));
  provide(CURSOR_UPDATER, new BodyCursorUpdater(globalThis as unknown as Window));
};
