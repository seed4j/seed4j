import { Optional } from '@/shared/optional/domain/Optional';

export type Timeoutable = () => void;

export interface TimeoutListener {
  register(callable: Timeoutable, milliseconds: number): void;

  unregister(): void;
}

export type TimeoutLauncher = () => TimeoutListener;

export class Timeout implements TimeoutListener {
  private registration: Optional<ReturnType<typeof setTimeout>>;

  constructor() {
    this.registration = Optional.empty();
  }

  register(callable: Timeoutable, milliseconds: number): void {
    this.unregister();
    this.registration = Optional.of(setTimeout(() => callable(), milliseconds));
  }

  unregister(): void {
    this.registration.ifPresent(clearTimeout);
  }
}
