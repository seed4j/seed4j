import { AlertMessage } from '@/shared/alert/domain/AlertMessage';

export type Alerted = (message: AlertMessage) => void;
