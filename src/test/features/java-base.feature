Feature: Java base

  Scenario: Should add java base
    When I add Java base to default project
    Then I should have files in "src/main/java/tech/jhipster/chips/error/domain"
      | Assert.java                           |
      | MissingMandatoryValueException.java   |
      | AssertionException.java               |
      | NotAfterTimeException.java            |
      | NotBeforeTimeException.java           |
      | NullElementInCollectionException.java |
      | NumberValueTooHighException.java      |
      | NumberValueTooLowException.java       |
      | StringTooLongException.java           |
      | StringTooShortException.java          |
      | TooManyElementsException.java         |
    And I should have files in "src/main/java/tech/jhipster/chips/common/domain"
      | ChipsCollections.java |
    And I should have files in "src/test/java/tech/jhipster/chips/error/domain"
      | AssertTest.java                         |
      | MissingMandatoryValueExceptionTest.java |
    And I should have files in "src/test/java/tech/jhipster/chips"
      | UnitTest.java         |
      | ReplaceCamelCase.java |
