Feature: Vue

  Scenario: Should initialize Vue application
    When I generate vue application
    Then I should have files in "src/main/webapp"
      | index.html                |
    And I should have files in "src/main/webapp/app"
      | env.d.ts                  |
      | main.ts                   |
    And I should have files in "src/main/webapp/app/router"
      | router.ts                 |
    And I should have files in "src/main/webapp/app/http"
      | AxiosHttp.ts              |
    And I should have files in "src/main/webapp/app/common/domain"
      | Logger.ts                 |
      | Message.ts                |
    And I should have files in "src/main/webapp/app/common/secondary"
      | ConsoleLogger.ts          |
    And I should have files in "src/main/webapp/app/common/primary/app"
      | App.component.ts          |
      | App.html                  |
      | App.vue                   |
      | index.ts                  |



  Scenario: Should add jwt authentication to Vue application
    When I add jwt to vue application
    Then I should have files in "src/main/webapp/app/common/domain"
      | AuthenticationService.ts   |
      | JWTStoreService.ts         |
      | Login.ts                   |
      | User.ts                    |
      | README.md                  |
    And I should have files in "src/main/webapp/app/common/primary/homepage"
      | Homepage.component.ts      |
      | Homepage.html              |
      | Homepage.vue               |
      | index.ts                   |
    And I should have files in "src/main/webapp/app/common/primary/login"
      | Login.component.ts         |
      | Login.html                 |
      | Login.vue                  |
      | index.ts                   |
    And I should not have files in "src/main/webapp/app/common/secondary"
      | AuthenticationRepository.ts|
      | RestLogin.ts               |
      | UserDTO.ts                 |
