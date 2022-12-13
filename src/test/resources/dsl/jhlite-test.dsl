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