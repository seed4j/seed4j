const checkIsNaN = <Value>(value: Value) => typeof value === 'number' && Number.isNaN(value);

export abstract class Optional<Value> {
  static empty<Value>(): Optional<Value> {
    return new EmptyOptional();
  }

  static of<Value>(value: Value): Optional<Value> {
    return new ValuatedOptional(value);
  }

  static ofNullable<Value>(value: Value | undefined | null): Optional<Value> {
    if (value === undefined || value === null || checkIsNaN(value)) {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  abstract map<Output>(mapper: (value: Value) => Output): Optional<Output>;
  abstract flatMap<Output>(mapper: (feature: Value) => Optional<Output>): Optional<Output>;
  abstract or(factory: () => Optional<Value>): Optional<Value>;
  abstract orElse(value: Value): Value;
  abstract orElseGet(factory: () => Value): Value;
  abstract orElseThrow<U = Error>(throwable?: () => U): Value;
  abstract filter(predicate: (value: Value) => boolean): Optional<Value>;
  abstract isEmpty(): boolean;
  abstract isPresent(): boolean;
  abstract ifPresent(consumer: (feature: Value) => void): void;
  abstract toArray(): Value[];
}

class EmptyOptional<Value> extends Optional<Value> {
  map<Output>(): Optional<Output> {
    return Optional.empty();
  }

  flatMap<Output>(): Optional<Output> {
    return Optional.empty();
  }

  or(factory: () => Optional<Value>): Optional<Value> {
    return factory();
  }

  orElse(value: Value): Value {
    return value;
  }

  orElseGet(factory: () => Value): Value {
    return factory();
  }

  orElseThrow<U>(throwable?: () => U): Value {
    if (throwable === undefined) {
      throw new Error("Can't get value from an empty optional");
    }

    throw throwable();
  }

  filter(): Optional<Value> {
    return this;
  }

  isEmpty(): boolean {
    return true;
  }

  isPresent(): boolean {
    return false;
  }

  ifPresent(): void {
    // Nothing to do
  }

  toArray(): Value[] {
    return [];
  }
}

class ValuatedOptional<Value> extends Optional<Value> {
  constructor(private readonly value: Value) {
    super();
  }

  map<Output>(mapper: (value: Value) => Output): Optional<Output> {
    return Optional.of(mapper(this.value));
  }

  flatMap<Output>(mapper: (feature: Value) => Optional<Output>): Optional<Output> {
    return mapper(this.value);
  }

  or(): Optional<Value> {
    return Optional.of(this.value);
  }

  orElse(): Value {
    return this.value;
  }

  orElseGet(): Value {
    return this.value;
  }

  orElseThrow(): Value {
    return this.value;
  }

  filter(predicate: (value: Value) => boolean): Optional<Value> {
    if (predicate(this.value)) {
      return this;
    }

    return Optional.empty();
  }

  isEmpty(): boolean {
    return false;
  }

  isPresent(): boolean {
    return true;
  }

  ifPresent(consumer: (feature: Value) => void): void {
    consumer(this.value);
  }

  toArray(): Value[] {
    return [this.value];
  }
}
