import { Alerted } from '@/shared/alert/domain/Alerted';
import { Unsubscribe } from '@/shared/alert/domain/Unsubscribe';

export interface AlertListener {
  onSuccess(alerted: Alerted): Unsubscribe;

  onError(alerted: Alerted): Unsubscribe;
}
