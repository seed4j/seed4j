Feature: Vue

  Scenario: Should apply vue core module
    When I apply modules to default project
      | init       |
      | prettier   |
      | typescript |
      | vue-core   |
    Then I should have files in "src/main/webapp/app"
      | AppVue.vue |

  Scenario: Should apply pinia module
    Given I apply modules to default project
      | init       |
      | prettier   |
      | typescript |
      | vue-core   |
    When I apply "vue-pinia" module without parameters to last project
    Then I should have "import piniaPersist from 'pinia-plugin-persistedstate'" in "src/main/webapp/app/main.ts"
