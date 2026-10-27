# Property based testing with jqwik

Property-based testing takes a very different approach from classical example-based testing: a single test is run hundreds of times with randomly generated inputs.
It allows checking that the system under test abides by certain properties that should hold true for all possible inputs.

Here are two (surprisingly failing) examples of simple property-based tests from [jqwik User Guide](https://jqwik.net/docs/current/user-guide.html):

```java
import static org.assertj.core.api.Assertions.assertThat;

import net.jqwik.api.*;

class PropertyBasedTests {

  @Property
  boolean absoluteValueOfAllNumbersIsPositive(@ForAll int anInteger) {
    return Math.abs(anInteger) >= 0;
  }

  @Property
  void lengthOfConcatenatedStringIsGreaterThanLengthOfEach(@ForAll String string1, @ForAll String string2) {
    String concatenated = string1 + string2;
    assertThat(concatenated.length()).isGreaterThan(string1.length());
    assertThat(concatenated.length()).isGreaterThan(string2.length());
  }
}
```
