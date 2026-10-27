import { provide } from '@/injections';
import { Timeout, TimeoutListener } from '@/shared/toast/domain/Timeout';
import { key } from 'piqure';

export const TIMEOUT = key<TimeoutListener>('Timeout');

export const provideForToast = (): void => {
  provide(TIMEOUT, new Timeout());
};
