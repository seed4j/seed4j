# Kipe Authorization

Kipe can be pronounced, in French, as "qui peut" which means: who can.

Kipe authorization helps define users authorizations which can be used to define who can do what on which resource.

To define your authorizations, you first have to define one (or more) `Resource`:

```java
enum MyResource implements Resource {
  USERS("my-users");

  private final String key;

  TestResource(String key) {
    this.key = key;
  }

  @Override
  public String key() {
    return key;
  }
}

```

You'll then have to define one (or more) `RolesAccesses` spring beans:

```java
@Configuration
class RolesConfiguration {

  @Bean
  RolesAccesses myRoles() {
    // @formatter:off
    return RolesAccesses.builder()
        .role(Role.ADMIN)
          .allAuthorized("read", MyResource.USERS)
          .and()
        .role(Role.USER)
          .specificAuthorized("read", MyResource.USERS)
          .and()
        .build();
    // @formatter:on
  }
}
```

For each role, you'll be able to define multiple accesses. There are three things defining an access:

- The scope: can be `all` or `specific`;
- The action, it's a verb (`read` in the example);
- The `Resource`.

Once you have defined one (or more) `RolesAccesses` you can inject `FullappAuthorizations` and check for authorizations. If you use `kipe-authorization` along with `kipe-expression` you'll be able to define `AccessChecker` looking like this:

```java
@Component
class MyObjectAccessChecker implements AccessChecker<MyObject> {

  private final FullappAuthorizations authorizations;

  public MyObjectAccessChecker(FullappAuthorizations authorizations) {
    this.authorizations = authorizations;
  }

  @Override
  public boolean can(AccessContext<MyObject> access) {
    if (authorizations.allAuthorized(access.authentication(), access.action(), MyResource.MY_OBJECT)) {
      return true;
    }

    if (authorizations.specificAuthorized(access.authentication(), access.action(), MyResource.MY_OBJECT)) {
      return businessFacingAuthorizationCheck(element);
    }

    return false;
  }
}

```

This way, you'll have fine-grained, easy to test authorizations check allowing custom configuration for each action in your application.
