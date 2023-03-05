# Kipe Expression

Kipe can be pronounced, in french, as "qui peut" which means: who can.  

It is an authorization evaluation mechanism build on top of spring security. Kipe is there to ease business facing authorization checks.  

It will allow you to use a new `can('action', #element)` expression in `@PreAuthorize` and `@PostAuthorize`:

```java
@PreAuthorize("can('update', #dummy)")
public void update(KipeDummy dummy) {
  // ...
}
```

Those expressions will then be evaluated in dedicated `AccessChecker`. To do so, you'll have to define spring beans (`@Component` or `@Service`) implementing `AccessChecker<T>`:

```java
@Component
class KipeDummyAccessChecker implements AccessChecker<KipeDummy> {

  @Override
  public boolean can(AccessContext<KipeDummy> access) {
    //TODO: your business authorization logic
  }
}
```

Kipe's only job is to call the `AccessChecker` for your object class.
