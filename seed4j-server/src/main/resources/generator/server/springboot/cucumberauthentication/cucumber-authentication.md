# Cucumber authentication

You can authenticate users in cucumber scenarios using:

```
Given I am logged in as "user"
```

The user and roles you'll get are defined in `AuthenticationSteps`, feel free to add new test users (with roles) in the `users` variable here.

You can "disconnect" using:

```
Given I am logged out
```
