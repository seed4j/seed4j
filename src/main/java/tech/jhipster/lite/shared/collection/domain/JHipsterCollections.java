package tech.jhipster.lite.shared.collection.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class JHipsterCollections {

  private JHipsterCollections() {}

  public static <T> Collection<T> immutable(Collection<T> collection) {
    if (collection == null) {
      return List.of();
    }

    return Collections.unmodifiableCollection(collection);
  }

  public static <K, V> Map<K, V> immutable(Map<K, V> map) {
    if (map == null) {
      return Map.of();
    }

    return Collections.unmodifiableMap(map);
  }
}
