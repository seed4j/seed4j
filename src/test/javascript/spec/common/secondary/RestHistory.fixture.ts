import { RestHistory } from '@/common/secondary/RestHistory';

export const createRestHistory = (restHistory?: Partial<RestHistory>): RestHistory => ({
  serviceIds: ['init', 'java-base', 'maven-java'],
  ...restHistory,
});
