package com.seed4j.module.domain.resource;

/**
 * Declares a Seed4J module.
 *
 * <p>
 * Implementations must be public, have a public no-argument constructor and be registered as a
 * {@link java.util.ServiceLoader} service, for example with {@code @AutoService(Seed4JModuleResourceProvider.class)}.
 * Declare one provider per module.
 * </p>
 *
 * <pre>{@code
 * @AutoService(Seed4JModuleResourceProvider.class)
 * public class MyModuleResource implements Seed4JModuleResourceProvider {
 *
 *   @Override
 *   public Seed4JModuleResource get(Seed4JModuleServices services) {
 *     return Seed4JModuleResource.builder()
 *       .slug(MY_MODULE)
 *       .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
 *       .apiDoc("My group", "Add my module")
 *       .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
 *       .tags("server")
 *       .factory(new MyModuleFactory(services)::buildModule);
 *   }
 * }
 * }</pre>
 */
@FunctionalInterface
public interface Seed4JModuleResourceProvider {
  /**
   * Get the module resource.
   *
   * @param services
   *          services available to build the module
   * @return The module resource
   */
  Seed4JModuleResource get(Seed4JModuleServices services);
}
