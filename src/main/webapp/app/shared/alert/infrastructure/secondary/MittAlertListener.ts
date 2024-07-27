import { Alerted } from '@/shared/alert/domain/Alerted';
import { Unsubscribe } from '@/shared/alert/domain/Unsubscribe';
import { Emitter, Handler } from 'mitt';
import { AlertMessage } from '@/shared/alert/domain/AlertMessage';
import { AlertType } from '@/shared/alert/infrastructure/secondary/AlertType';
import { AlertListener } from '@/shared/alert/domain/AlertListener';

export class MittAlertListener implements AlertListener {
  constructor(private emitter: Emitter<any>) {}

  onSuccess(alerted: Alerted): Unsubscribe {
    const handler: Handler<AlertMessage> = message => alerted(message);
    this.emitter.on(AlertType.SUCCESS, handler);
    return () => {
      this.emitter.off(AlertType.SUCCESS, handler);
    };
  }

  onError(alerted: Alerted): Unsubscribe {
    const handler: Handler<AlertMessage> = message => alerted(message);
    this.emitter.on(AlertType.ERROR, handler);
    return () => {
      this.emitter.off(AlertType.ERROR, handler);
    };
  }
}
