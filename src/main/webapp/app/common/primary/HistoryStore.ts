import { defineStore } from 'pinia';
import { History } from '@/common/domain/History';

const emptyHistory = (): History => ({
  services: [],
});

export const useHistoryStore = defineStore('HistoryStore', {
  state: () => {
    return {
      history: emptyHistory(),
    };
  },

  getters: {
    getHistory: state => state.history,
  },

  actions: {
    async setHistory(history: History) {
      this.history = history;
    },
  },
});
