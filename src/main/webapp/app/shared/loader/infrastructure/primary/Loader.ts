export class Loader<T> {
  private assignedValue?: T;

  private constructor(private loading: boolean) {}

  public static loading<T>(): Loader<T> {
    return new Loader(true);
  }

  public static loaded<T>(value: T): Loader<T> {
    const result = this.loading<T>();

    result.loaded(value);

    return result;
  }

  isLoading(): boolean {
    return this.loading;
  }

  loaded(value: T) {
    this.assignedValue = value;

    this.loading = false;
  }

  value(): T {
    if (this.assignedValue === undefined) {
      throw new Error('Trying to get the value of a loading item');
    }

    return this.assignedValue;
  }
}
