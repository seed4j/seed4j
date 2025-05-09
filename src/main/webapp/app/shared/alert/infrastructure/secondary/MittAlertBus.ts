import { Emitter } from 'mitt';

import { AlertBus } from '@/shared/alert/domain/AlertBus';
import { AlertMessage } from '@/shared/alert/domain/AlertMessage';
import { AlertType } from '@/shared/alert/infrastructure/secondary/AlertType';

export class MittAlertBus implements AlertBus {
  constructor(private readonly emitter: Emitter<any>) {}

  success(alert: AlertMessage): void {
    this.emitter.emit(AlertType.SUCCESS, alert);
  }

  error(alert: AlertMessage): void {
    this.emitter.emit(AlertType.ERROR, alert);
  }
}
