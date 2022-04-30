import { History } from '@/common/domain/History';
import { Service } from '@/common/domain/Service';

export const createHistory = (history?: Partial<History>): History => ({
  services: [Service.INITIALIZATION, Service.JAVA_BASE, Service.MAVEN_JAVA],
  ...history,
});
