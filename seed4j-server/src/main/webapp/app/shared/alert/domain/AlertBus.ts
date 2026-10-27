import { AlertMessage } from '@/shared/alert/domain/AlertMessage';

export interface AlertBus {
  success(alertMessage: AlertMessage): void;

  error(alertMessage: AlertMessage): void;
}
