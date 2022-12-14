### Syntax

Enumeration declaration is done as follows:

```
enum <enum name> {
  <ENUM KEY> [(<enum value>)]
}
```

- Enumeration entry values are mandatory
    - And uppercase keys must be used
- Enumeration entry values are optional, and must be wrapped inside parenthesises

---

### Examples

#### Basic example

```dsl
enum Country {
  BELGIUM,
  FRANCE,
  ITALY
}
```

And its use:

```dsl
enum Country {}

entity A {
  country Country
}
```

---

#### With values

Enum values can have explicit String values:

```dsl
enum Country {
  BELGIUM ("Belgium")
  FRANCE ("France")
  ITALY ("Italy")
}
```

---

#### Commenting

Just like class and fields, commenting is possible for enums, with the same rules.

Comments will later be added as Javadoc comments by JHipster. The JDL possesses its own kind of comment:
- // an ignored comment
- /** not an ignored comment */

Therefore, anything that starts with `//` is considered an internal comment for JDL, and will not be counted as Javadoc.
Please note that the JDL Studio directives that start with `#` will be ignored during parsing.

```dsl
/** This comment will be taken into account */
enum Country {
  // But not this one!
  FRANCE
}
```