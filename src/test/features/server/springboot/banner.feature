Feature: Banner

  Scenario: Should add banner JHipster v7
    When I apply "banner-jhipster-v7" module to default project without parameters
    Then I should have files in "src/main/resources"
      | banner.txt |

  Scenario: Should add banner JHipster v7 for React
    When I apply "banner-jhipster-v7-react" module to default project without parameters
    Then I should have files in "src/main/resources"
      | banner.txt |

  Scenario: Should add banner JHipster v7 for Vue
    When I apply "banner-jhipster-v7-vue" module to default project without parameters
    Then I should have files in "src/main/resources"
      | banner.txt |

  Scenario: Should add banner JHipster v2
    When I apply "banner-jhipster-v2" module to default project without parameters
    Then I should have files in "src/main/resources"
      | banner.txt |

  Scenario: Should add banner JHipster v3
    When I apply "banner-jhipster-v3" module to default project without parameters
    Then I should have files in "src/main/resources"
      | banner.txt |
