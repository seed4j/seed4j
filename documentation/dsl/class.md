# Usage

## Syntax

The Class declaration is done as follows:
```
[<class javadoc>]
[<class annotation>*]
[class | record] <class name> {
  [<field javadoc>]
  [<field annotation>*]
  <field name> <field type> [<validation>*]
}
```

- `<class name>` the name of the class or record,
- `<field name>` the name of one field of the entity,
- `<field type>` the JHipster supported type of the field,
- and as an option:
    - `<class javadoc>` the documentation of the entity,
    - `<class annotation>` the options for the class (see [Annotations](#annotations) for a complete list of available options),
    - `<field javadoc>` the documentation of the field,
    - `<field annotation>` the options for the field,
    - `<validation>` the validations for the field.


### Annotations

- `@package(your.package.for.this.class)` the package where this class will be generated
- `@ignore` This class will not be generated. Useful with package for have import when use.

### Examples

#### Basic example

```dsl
class myClass
```

#### With fields

```dsl
class myClass {
  name String required
  age Integer
}
```

---

#### With field validations

```dsl
class myClass{
  name String required
  age Integer min(10) max(42)
}
```

---

#### Blob declaration

TODO

---

#### Regular expressions

This is a certain validation (only available to String types), and its syntax is:

```dsl
class myEntity {
  name String pattern("/^[A-Z][a-z]+\d$/")
}
```

Let's break it down:
- `pattern` is the keyword to declare a regex validation (with the normal parenthesises)
- `"/.../"` the pattern is declared inside `"/` and `/"`
- `\` anti-slashes needn't be escaped

---

#### Commenting

Commenting is possible in the DSl for class/record/enum and fields, and will generate documentation (Javadoc or JSDoc, depending
on the backend).

```dsl
/**
 * This is a comment
 * about a class
 * @author Someone
 */
class myClass {
  /**
   * This comment will also be used!
   * @type...
   */
   name String
   age Integer // this is yet another comment
}
```
These comments will later be added as Javadoc comments by JHipster. The Dsl possesses its own kind of comment:
- // an ignored comment
- /** not an ignored comment */

Therefore, anything that starts with `//` is considered an internal comment for JDL, and will not be counted as Javadoc.

Another form of comments are the following comments: Note the `,` after the comment.
Without the `,` at the end, the comment `/** This is comment for name */` would be associated with property `count`
```
class myClass {
  name String /** This is comment for name */,
  count Integer /**This is comment for count */,
}
```

Here myClass's name will be commented with `This is comment for name`, and count with `This is comment for count`.

Without the `,`, name would have no comment and count would have both. 

### Field annotations

- `@min`
- `@max`
- `@decimalmin`
- `@decimalmax`
- `@before`
- `@past`
- `@future`
- `@futureOrPresent`
- `@PastOrPresent`
- `@notEmpty`
- `@negative`
- `@positive`
- `@assertFalse`
- `@assertTrue`
- `@notBlank`
- `@nullable`
- `@notNull`


### Field types and validations

Each field type has its own validation list. Here are the types supported in the JDL:

<table class="table table-striped table-responsive">
  <tr>
    <th>JDL type</th>
    <th>Validations</th>
  </tr>
  <tr>
    <td>String</td>
    <td><dfn>required, minlength, maxlength, unique, pattern</dfn></td>
  </tr>
  <tr>
    <td>Integer</td>
    <td><dfn>required, min, max, unique</dfn></td>
  </tr>
  <tr>
    <td>Long</td>
    <td><dfn>required, min, max, unique</dfn></td>
  </tr>
  <tr>
    <td>BigDecimal</td>
    <td><dfn>required, min, max, unique</dfn></td>
  </tr>
  <tr>
    <td>Float</td>
    <td><dfn>required, min, max, unique</dfn></td>
  </tr>
  <tr>
    <td>Double</td>
    <td><dfn>required, min, max, unique</dfn></td>
  </tr>
  <tr>
    <td>Enum</td>
    <td><dfn>required, unique</dfn></td>
  </tr>
  <tr>
    <td>Boolean</td>
    <td>required, unique</td>
  </tr>
  <tr>
    <td>LocalDate</td>
    <td><dfn>required, unique</dfn></td>
  </tr>
  <tr>
    <td>ZonedDateTime</td>
    <td><dfn>required, unique</dfn></td>
  </tr>
  <tr>
    <td>Instant</td>
    <td><dfn>required, unique</dfn></td>
  </tr>
  <tr>
    <td>Duration</td>
    <td><dfn>required, unique</dfn></td>
  </tr>
  <tr>
    <td>UUID</td>
    <td><dfn>required, unique</dfn></td>
  </tr>
  <tr>
    <td>Blob</td>
    <td><dfn>required, minbytes, maxbytes, unique</dfn></td>
  </tr>
  <tr>
    <td>AnyBlob</td>
    <td><dfn>required, minbytes, maxbytes, unique</dfn></td>
  </tr>
  <tr>
    <td>ImageBlob</td>
    <td><dfn>required, minbytes, maxbytes, unique</dfn></td>
  </tr>
  <tr>
    <td>TextBlob</td>
    <td><dfn>required, unique</dfn></td>
  </tr>
</table>
```
