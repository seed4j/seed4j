package com.seed4j.shared.memoizer.domain;

import com.seed4j.shared.error.domain.Assert;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Memoizers {

  private Memoizers() {}

  public static <Result> Supplier<Result> of(Supplier<Result> supplier) {
    Assert.notNull("supplier", supplier);

    return () -> of(__ -> supplier.get()).apply(null);
  }

  public static <Input, Result> Function<Input, Result> of(Function<Input, Result> function) {
    Assert.notNull("function", function);

    return new MemoizedFunction<>(function);
  }

  private static final class MemoizedFunction<Input, Result> implements Function<Input, Result> {

    private final Function<Input, Result> function;
    private final Map<MemoizedInput<Input>, MemoizedResult<Result>> results = new ConcurrentHashMap<>();

    private MemoizedFunction(Function<Input, Result> function) {
      this.function = function;
    }

    @Override
    public Result apply(Input input) {
      return results.computeIfAbsent(new MemoizedInput<>(input), this::toMemoizedResult).result();
    }

    private MemoizedResult<Result> toMemoizedResult(MemoizedInput<Input> input) {
      return new MemoizedResult<>(function.apply(input.input()));
    }

    private record MemoizedInput<Input>(Input input) {}

    private record MemoizedResult<Result>(Result result) {}
  }
}
