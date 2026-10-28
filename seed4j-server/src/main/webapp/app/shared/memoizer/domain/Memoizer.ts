export class Memoizer<Key, Value> {
  private readonly memoizedValues = new Map<Key, Value>();

  get(key: Key, factory: () => Value): Value {
    const memoizedValue = this.memoizedValues.get(key);

    if (memoizedValue) {
      return memoizedValue;
    }

    const value = factory();
    this.memoizedValues.set(key, value);

    return value;
  }
}
