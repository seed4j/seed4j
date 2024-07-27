import { key } from 'piqure';
import { Timeout, TimeoutListener } from '@/shared/toast/domain/Timeout';
import { provide } from '@/injections';

export const TIMEOUT = key<TimeoutListener>('Timeout');

export const provideForToast = (): void => {
  provide(TIMEOUT, new Timeout());
};
