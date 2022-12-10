config {
baseName=Override
basePackageName=Override
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
          name String

        }

       /**
       * comment
       */
       @package(test)
       record test {
          @min(10)
          @max(20)
          shipId Integer

          @notBlank
          name String

        }
    }
}

context ctx2 {
    domain {
    }
}