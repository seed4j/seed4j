package com.seed4j.shared.enumeration.domain;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.nullness.domain.Contract;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;

public final class Enums {

  private static final EnumMappings MAPPINGS = new EnumMappings();

  private Enums() {}

  @Contract("!null, _ -> !null")
  public static <From extends Enum<From>, To extends Enum<To>> @Nullable To map(@Nullable Enum<From> from, Class<To> to) {
    Assert.notNull("to", to);

    if (from == null) {
      return null;
    }

    return MAPPINGS.get(from, to);
  }

  private static final class EnumMappings {

    private final Map<CacheKey<?, ?>, Map<Enum<?>, Enum<?>>> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    private <From extends Enum<From>, To extends Enum<To>> @Nullable To get(Enum<From> from, Class<To> to) {
      CacheKey<From, To> key = new CacheKey<>(from.getDeclaringClass(), to);
      return (To) cache.computeIfAbsent(key, buildCache(from)).get(from);
    }

    private Function<CacheKey<?, ?>, Map<Enum<?>, Enum<?>>> buildCache(Enum<?> from) {
      return key -> {
        try {
          return Arrays.stream(key.from().getEnumConstants()).collect(
            Collectors.toMap(Function.identity(), source -> Enum.valueOf(key.to(), source.name()))
          );
        } catch (IllegalArgumentException _) {
          throw new UnmappableEnumException(from.getDeclaringClass(), key.to());
        }
      };
    }

    private record CacheKey<From extends Enum<From>, To extends Enum<To>>(Class<From> from, Class<To> to) {}
  }
}
