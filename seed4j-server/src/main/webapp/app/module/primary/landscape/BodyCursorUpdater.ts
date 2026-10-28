import { CursorType } from '@/module/primary/landscape/CursorType';

export class BodyCursorUpdater {
  constructor(private readonly window: Window) {}

  set(cursorType: CursorType) {
    this.window.document.body.style.cursor = cursorType;
  }

  reset() {
    this.window.document.body.style.cursor = 'auto';
  }
}
