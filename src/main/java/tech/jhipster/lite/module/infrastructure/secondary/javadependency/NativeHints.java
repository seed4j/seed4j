package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import org.dom4j.DocumentFactory;
import org.dom4j.util.NonLazyDocumentFactory;
import org.dom4j.util.ProxyDocumentFactory;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native runtime hints")
class NativeHints implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
    // Needed for xml reading and writing via dom4j
    hints
      .reflection()
      .registerType(ProxyDocumentFactory.class, MemberCategory.values())
      .registerType(DocumentFactory.class, MemberCategory.values())
      .registerType(NonLazyDocumentFactory.class, MemberCategory.values());

    // internal usage
    hints.reflection().registerType(TypeReference.of("com.sun.org.apache.xpath.internal.functions.FuncLocalPart"), MemberCategory.values());

    // patterns for our own template files
    hints.resources().registerPattern("generator/**");
  }
}
