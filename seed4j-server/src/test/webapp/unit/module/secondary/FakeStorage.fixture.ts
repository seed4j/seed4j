export const fakeStorage = (): Storage => {
  let store: { [key: string]: string } = {};
  return {
    getItem: (key: string) => store[key],
    setItem: (key: string, value: string) => (store[key] = value),
    removeItem: (key: string) => delete store[key],
    clear: () => (store = {}),
    get length() {
      return Object.keys(store).length;
    },
    key: (index: number) => Object.keys(store)[index],
  };
};
