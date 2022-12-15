config {
baseName=baseNameOverride
basePackageName=basePackageNameOverride
packageInfrastructureName=infrastructureOverride
packageInfrastructurePrimaryName=primaryOverride
packageInfrastructureSecondaryName=secondaryOverride
packageDomainName=domainOverride
useFluentMethod=false
projectFolder=/tmp/myApp
useAssertAsValidation=yes
}

modules {
// not implemented yet
}

context ctx1 {

    domain {

        class Ship(parentClass) {  // (parentClass) only in grammar for now
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
           class MyClass {// @crud @create @read @update @delete only in grammar for now

              shipId test

              @notNull
              ship Ship

              @notNull
              myEnum MyEnum

            }
    }

    primary {
             // from MyCl  only in grammar for now
             //  fromDomain only in grammar for now

            class MyPrimaryClass {

               shipId test

               @notNull
               ship Ship

               @notNull
               myEnum MyEnum

             }
               enum MyPrimaryEnum {
             DD,EE,FF
         }
    }
    secondary {
             // from MyCl  only in grammar for now
             //  fromDomain only in grammar for now
           class MySecondaryClass {

                shipId test

                @notNull
                ship Ship

                @notNull
                myEnum MyEnum

              }
                enum MySecondaryEnum {
                   GG,HH,JJ
               }
    }

}