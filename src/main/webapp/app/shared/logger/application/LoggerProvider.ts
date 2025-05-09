import { key } from 'piqure';

import { provide } from '@/injections';
import { Logger } from '@/shared/logger/domain/Logger';
import ConsoleLogger from '@/shared/logger/infrastructure/secondary/ConsoleLogger';

export const LOGGER = key<Logger>('Logger');

export const provideForLogger = (): void => {
  provide(LOGGER, new ConsoleLogger(console));
};
