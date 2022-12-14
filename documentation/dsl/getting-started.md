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
baseName=baseNameOverride
basePackageName=basePackageNameOverride
packageDomainName=domainOverride
useFluentMethod=false
projectFolder=/tmp/myApp
useAssertAsValidation=yes
}

context ctx1 {

    domain {

        class Ship {
          @min(10)
          @max(20)
          shipId Integer

          @notNull
          name Test

        }

       /**
       * comment
       */
       @package(test)
       record test {
          @min(10)
          @max(20)
          shipId Integer

          @notNull
          myEnum MyEnum

        }
    }
}

context ctx2 {
    domain {
            enum MyEnum {
                AAA,BB,CCC
            }
           record MyClass {

              shipId test

              @notNull
              ship Ship

              @notNull
              myEnum MyEnum

            }
    }
}
```


