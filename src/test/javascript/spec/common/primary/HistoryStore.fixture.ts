import sinon, { SinonStub } from 'sinon';

export interface HistoryStoreFixture {
  getHistory: SinonStub;
  setHistory: SinonStub;
}

export const stubHistoryStore = (): HistoryStoreFixture =>
  ({
    getHistory: sinon.stub(),
    setHistory: sinon.stub(),
  } as HistoryStoreFixture);
