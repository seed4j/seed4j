
config {
    useFluentMethod=no
    baseName=Override
    basePackageName=Override
    packageInfrastructureName=infrastructureOverride
    packageInfrastructurePrimaryName=primaryOverride
    packageInfrastructureSecondaryName=secondaryOverride
    packageDomainName=domainOverride
}


context MyContext {

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
