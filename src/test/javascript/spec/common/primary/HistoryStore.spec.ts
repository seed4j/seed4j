import { createPinia, setActivePinia } from 'pinia';
import { useHistoryStore } from '@/common/primary/HistoryStore';
import { History } from '@/common/domain/History';
import { createHistory } from '../domain/History.fixture';

describe('HistoryStore', () => {
  beforeEach(() => {
    setActivePinia(createPinia());
  });

  it('should have an empty history by default', () => {
    const historyStore = useHistoryStore();

    expect(historyStore.history).toEqual<History>({
      services: [],
    });
  });

  it('should get history', () => {
    const historyStore = useHistoryStore();

    expect(historyStore.getHistory).toEqual<History>({
      services: [],
    });
  });

  it('should set history', () => {
    const historyStore = useHistoryStore();
    const history = createHistory();

    historyStore.setHistory(history);

    expect(historyStore.getHistory).toEqual<History>(history);
  });
});
