# Approval based testing with ApprovalTests

Instead of writing individual assertions, ApprovalTests focuses on verifying the overall output. This is particularly useful when testing complex objects, files, or outputs where writing assertions would be tedious.

## Basic Example

Here is an example of approval-based tests from [ApprovalTests.java User Guide](https://github.com/approvals/ApprovalTests.Java/blob/master/README.md)

```java
import java.util.Arrays;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class SampleArrayTest {

  @Test
  void verifyArraySorting() {
    String[] names = { "Llewellyn", "James", "Dan", "Jason", "Katrina" };
    Arrays.sort(names);
    Approvals.verifyAll("", names);
  }
}
```

This will produce a file `SampleArrayTest.verifyArraySorting.received.txt`:

```
[0] = Dan
[1] = James
[2] = Jason
[3] = Katrina
[4] = Llewellyn
```

Simply rename this to `SampleTest.verifyArraySorting.approved.txt` and the test will now pass.
The `*.approved.*` files should be committed to source control, and updated whenever the inputs or the logic of the test change.

## Usage with Parameterized Tests

By default, ApprovalTests generates one file per test.
For @ParameterizedTests, we want to generate multiple files for a single parameterized test, where each filename includes the test parameters.

```java
@ParameterizedTest
@ValueSource(strings = { "parameter1", "parameter2" })
void sampleParameterizedTest(String parameter) {
  Object output = Approvals.verify(output, Approvals.NAMES.withParameters(parameter)); // ... your code goes here
}
```

This will generate files `sampleParameterizedTest.parameter1.approved.txt` and `sampleParameterizedTest.parameter2.approved.txt`.

## Test Combinations

ApprovalTests supports test combinations, where multiple parameters are used to generate a single file.
See [ApprovalTests.java User Guide](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/README.md#test-combinations) for more details.

```java
@Test
void sampleCombinationCheck(String parameter) {
  String[] strings = { "hello", "world" };
  Integer[] numbers = { 1, 2, 3 };
  CombinationApprovals.verifyAllCombinations((string, number) -> "(%s %s)".formatted(string, number), strings, numbers);
}
```

The approved `sampleCombinationCheck.approved.txt` file will contain:

```
[hello, 1] => (hello 1)
[hello, 2] => (hello 2)
[hello, 3] => (hello 3)
[world, 1] => (world 1)
[world, 2] => (world 2)
[world, 3] => (world 3)
```
