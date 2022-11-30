# Usage

## installation

For generate the grammar and lexer code you need to execute

```bash
./mvnw antlr4:antlr4
```

For the moment only the creation of an object representing the generated application is created.

To see what is done you can run the test `shouldCreateAValidDslApp` in `FileSytemDslRepositoryTest.class`
`

Example of file:

```dsl
config {
baseName=Override
basePackageName=Override
packageInfrastructureName=infrastructureOverride
packageInfrastructurePrimaryName=primaryOverride
packageInfrastructureSecondaryName=secondaryOverride
packageDomainName=domainOverride
useFluentMethod=false
projectFolder=/tmp/myApp
}

context ctx1 {

    domain {

        @test
        class Ship {
          @notBlank
          shipId UUID

          @notBlank
          name String

          @nullable
          port Port
        }

       /**
       * comment
       */
       @package(test)
       record test {
          @notBlank
          shipId UUID

          @notBlank
          name String

          @nullable
          port Port
        }
    }
}

context ctx2 {
    domain {
    }
}
```


