export abstract class Loader<T> {
  static loading<T>(): Loader<T> {
    return new LoadingLoader();
  }

  static loaded<T>(value: T): Loader<T> {
    return new LoadedLoader(value);
  }

  static error<T>(error: Error): Loader<T> {
    return new ErrorLoader<T>(error);
  }

  get isLoading(): boolean {
    return this.state === LoadingState.LOADING;
  }

  get isLoaded(): boolean {
    return this.state === LoadingState.LOADED;
  }

  get isInError(): boolean {
    return this.state === LoadingState.ERROR;
  }

  abstract get state(): LoadingState;

  abstract get value(): T;

  abstract get error(): Error;
}

export enum LoadingState {
  LOADING,
  LOADED,
  ERROR,
}

class LoadingLoader<T> extends Loader<T> {
  get state(): LoadingState {
    return LoadingState.LOADING;
  }

  get value(): T {
    throw new Error("Can't get value for a loading loaded");
  }

  get error(): Error {
    throw new Error("Can't get error for loading loader");
  }
}

class LoadedLoader<T> extends Loader<T> {
  constructor(private readonly _value: T) {
    super();
  }

  get state(): LoadingState {
    return LoadingState.LOADED;
  }

  get value(): T {
    return this._value;
  }

  get error(): Error {
    throw new Error("Can't get error for loaded loader");
  }
}

class ErrorLoader<T> extends Loader<T> {
  constructor(private readonly _error: Error) {
    super();
  }

  get state(): LoadingState {
    return LoadingState.ERROR;
  }

  get value(): T {
    throw new Error("Can't get value for error loader");
  }

  get error(): Error {
    return this._error;
  }
}
