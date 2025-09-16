package com.seed4j.shared.collection.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JCollectionsTest {

  @Nested
  class ImmutableCollection {

    @Test
    void shouldGetEmptyImmutableCollectionFromNullCollection() {
      Collection<String> source = null;
      Collection<String> collection = Seed4JCollections.immutable(source);

      assertThat(collection).isEmpty();
      assertThatThrownBy(collection::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldGetImmutableCollectionFromCollection() {
      Collection<String> source = new ArrayList<>();
      source.add("test");
      Collection<String> collection = Seed4JCollections.immutable(source);

      assertThat(collection).containsExactly("test");
      assertThatThrownBy(collection::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldAcceptCovariantCollectionAsInput() {
      List<Long> inputCollection = List.of(1L);
      Collection<Number> collection = Seed4JCollections.immutable(inputCollection);

      assertThat(collection).containsExactly(1L);
    }
  }

  @Nested
  class ImmutableMap {

    @Test
    void shouldGetEmptyImmutableMapFromNullMap() {
      Map<String, String> source = null;
      Map<String, String> map = Seed4JCollections.immutable(source);

      assertThat(map).isEmpty();
      assertThatThrownBy(map::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldGetImmutableMapFromMap() {
      Map<String, String> source = new HashMap<>();
      source.put("key", "value");
      Map<String, String> map = Seed4JCollections.immutable(source);

      assertThat(map).containsExactly(entry("key", "value"));
      assertThatThrownBy(map::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldAcceptCovariantCollectionAsInput() {
      Map<Integer, Double> inputMap = Map.of(1, 1.0);
      Map<Number, Number> resultMap = Seed4JCollections.immutable(inputMap);

      assertThat(resultMap).containsExactly(entry(1, 1.0));
    }
  }

  @Nested
  class ConcatCollections {

    @Test
    void shouldGetEmptyCollectionFromNullCollections() {
      Collection<String> concatenatedCollection = Seed4JCollections.concat((Collection<String>) null);

      assertThat(concatenatedCollection).isEmpty();
    }

    @Test
    void shouldConcatCollections() {
      Collection<String> collection = Seed4JCollections.concat(List.of("first"), Set.of("second"));

      assertThat(collection).containsExactly("first", "second");
      assertThatThrownBy(collection::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldConcatCollectionsWithCovariance() {
      List<Long> longs = List.of(1L);
      List<Double> doubles = List.of(2.0);
      Collection<Number> collection = Seed4JCollections.concat(longs, doubles);

      assertThat(collection).containsExactly(1L, 2.0);
    }

    @Test
    void shouldIgnoreNullInputs() {
      Collection<String> collection = Seed4JCollections.concat(List.of("first"), null);

      assertThat(collection).containsExactly("first");
    }
  }

  @Nested
  class ConcatMaps {

    @Test
    void shouldGetEmptyMapFromNullMaps() {
      Map<Number, String> concatenatedMap = Seed4JCollections.concat((Map<Number, String>) null);

      assertThat(concatenatedMap).isEmpty();
    }

    @Test
    void shouldConcatMaps() {
      Map<Number, String> map = Seed4JCollections.concat(Map.of(1, "first"), Map.of(2, "second"));

      assertThat(map).containsOnly(entry(1, "first"), entry(2, "second"));
      assertThatThrownBy(map::clear).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldConcatMapsWithCovariance() {
      Map<Integer, Long> first = Map.of(1, 1L);
      Map<Double, Float> second = Map.of(2.0, 2.0f);
      Map<Number, Number> map = Seed4JCollections.concat(first, second);

      assertThat(map).containsOnly(entry(1, 1L), entry(2.0, 2.0f));
    }

    @Test
    void shouldIgnoreNullInputs() {
      Map<Number, String> map = Seed4JCollections.concat(Map.of(1, "first"), null);

      assertThat(map).containsOnly(entry(1, "first"));
    }
  }
}
