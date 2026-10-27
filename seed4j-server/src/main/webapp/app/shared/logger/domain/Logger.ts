import { Message } from '@/shared/logger/domain/Message';

export interface Logger {
  error(message: Message, error: Error): void;
}
