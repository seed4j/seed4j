const orNull = <T>(option?: T) => {
  if (option === undefined) {
    return null;
  }
  return option;
};

export class FakeStorage implements Storage {
  private store: Record<string, string> = {};

  get length(): number {
    return Object.keys(this.store).length;
  }

  clear(): void {
    this.store = {};
  }

  getItem(key: string): string | null {
    return orNull(this.store[key]);
  }

  key(index: number): string | null {
    return this.getItem(Object.keys(this.store)[index]);
  }

  removeItem(key: string): void {
    delete this.store[key];
  }

  setItem(key: string, value: string): void {
    this.store[key] = value;
  }
}
