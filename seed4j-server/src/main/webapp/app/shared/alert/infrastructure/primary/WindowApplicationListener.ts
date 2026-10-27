import { ApplicationListener } from '@/shared/alert/infrastructure/primary/ApplicationListener';

export class WindowApplicationListener implements ApplicationListener {
  constructor(private readonly window: Window) {}

  addEventListener(type: string, listener: EventListenerOrEventListenerObject): void {
    this.window.addEventListener(type, listener);
  }
  removeEventListener(type: string, listener: EventListenerOrEventListenerObject): void {
    this.window.removeEventListener(type, listener);
  }
}
