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