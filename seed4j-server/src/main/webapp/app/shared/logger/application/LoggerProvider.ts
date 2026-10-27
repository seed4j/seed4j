import { provide } from '@/injections';
import { Logger } from '@/shared/logger/domain/Logger';
import ConsoleLogger from '@/shared/logger/infrastructure/secondary/ConsoleLogger';
import { key } from 'piqure';

export const LOGGER = key<Logger>('Logger');

export const provideForLogger = (): void => {
  provide(LOGGER, new ConsoleLogger(console));
};
