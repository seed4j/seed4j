import sinon, { SinonStub } from 'sinon';

import { ThemeRepository } from '@/module/domain/ThemeRepository';

export interface LocalWindowThemeRepositoryStub extends ThemeRepository {
  get: SinonStub;
  choose: SinonStub;
}

export const stubLocalWindowThemeRepository = (): LocalWindowThemeRepositoryStub => ({ get: sinon.stub(), choose: sinon.stub() });
