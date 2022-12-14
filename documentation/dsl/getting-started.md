# Usage

## installation

For generate the grammar and lexer code you need to execute

```bash
./mvnw antlr4:antlr4
```

Start jhipster lite
```
./mvnw
```


Create a DSL file (example):

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
          @min(10) @max(20)
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
- Edit the `PATH_TO_FILE` variable to match the path of the previously created file.
- Edit the `TARGET_FOLDER` variable to match the target folder

WARNING!! 

For now, the generator need module `init` and `prettier` in the target folder `TARGET_FOLDER`.

```bash
export PATH_TO_FILE=/projet/antarus/jhipster-lite/src/test/resources/dsl/jhlite-test.dsl
export TARGET_FOLDER=/tmp/test
curl --request POST \
--url http://localhost:7471/api/apply-dsl \
--header 'Content-Type: multipart/form-data' \
--form file=@$PATH_TO_FILE \
--form "properties={
\"projectFolder\":\"$TARGET_FOLDER\",
\"commit\":false
}"
```

