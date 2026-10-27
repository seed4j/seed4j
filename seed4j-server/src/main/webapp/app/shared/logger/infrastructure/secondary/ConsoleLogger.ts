import { Logger } from '@/shared/logger/domain/Logger';
import { Message } from '@/shared/logger/domain/Message';

export default class ConsoleLogger implements Logger {
  constructor(private readonly logger: Console) {}

  error(message: Message, error: Error) {
    this.logger.error(`${message}\n`, error);
  }
}
