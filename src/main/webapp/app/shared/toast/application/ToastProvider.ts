import { key } from 'piqure';

import { provide } from '@/injections';
import { Timeout, TimeoutListener } from '@/shared/toast/domain/Timeout';

export const TIMEOUT = key<TimeoutListener>('Timeout');

export const provideForToast = (): void => {
  provide(TIMEOUT, new Timeout());
};
