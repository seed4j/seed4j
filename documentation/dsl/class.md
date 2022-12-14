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
    - `<class annotation>` the options for the entity (see [Options][] for a complete list of available options),
    - `<field javadoc>` the documentation of the field,
    - `<field annotation>` the options for the field,
    - `<validation>` the validations for the field.

### Field types and validations
(From jhipster only pattern is not managed yet)

Each field type has its own validation list. Here are the types supported in the JDL:

<table class="table table-striped table-responsive">
  <tr>
    <th>JDL type</th>
    <th>Validations</th>
  </tr>
  <tr>
    <td>String</td>
    <td><dfn>required, minlength, maxlength, unique</dfn></td>
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
