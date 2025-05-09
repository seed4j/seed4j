import mitt from 'mitt';
import { key } from 'piqure';

import { provide } from '@/injections';
import { AlertBus } from '@/shared/alert/domain/AlertBus';
import { AlertListener } from '@/shared/alert/domain/AlertListener';
import { MittAlertBus } from '@/shared/alert/infrastructure/secondary/MittAlertBus';
import { MittAlertListener } from '@/shared/alert/infrastructure/secondary/MittAlertListener';

export const ALERT_BUS = key<AlertBus>('AlertBus');
export const ALERT_LISTENER = key<AlertListener>('AlertListener');

export const provideForAlerts = (): void => {
  const emitter = mitt();
  provide(ALERT_BUS, new MittAlertBus(emitter));
  provide(ALERT_LISTENER, new MittAlertListener(emitter));
};
