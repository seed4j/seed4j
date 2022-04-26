import { composeSelector, dataSelector } from '../support/selector';

const generatorSelector = (name: string) => dataSelector(composeSelector('generator', name));
const angularGeneratorSelector = (name: string) => dataSelector(composeSelector('angular-generator', name));

describe('Generator', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should redirect to generator by default', () => {
    cy.url().should('include', '/generator');
  });

  it('should display generator page', () => {
    cy.get(generatorSelector('title')).contains('JHipster lite');

    cy.get(generatorSelector('init-button')).contains('Init');
    cy.get(generatorSelector('add-maven-button')).contains('Maven');
    cy.get(generatorSelector('add-jacoco-button')).contains('JaCoCo');
    cy.get(generatorSelector('add-sonar-backend-button')).contains('Sonar Backend');
    cy.get(generatorSelector('add-sonar-backend-frontend-button')).contains('Sonar Backend+Frontend');
    cy.get(generatorSelector('add-java-base-button')).contains('Java Base');
    cy.get(generatorSelector('add-frontend-maven-plugin-button')).contains('Frontend Maven Plugin');
    cy.get(generatorSelector('download-button')).should('not.exist');
  });

  it('should display spring boot', () => {
    cy.get(generatorSelector('option-springboot')).check();
    cy.get(generatorSelector('add-spring-boot-button')).contains('Spring Boot');
    cy.get(generatorSelector('add-spring-boot-mvc-tomcat-button')).contains('Spring MVC Tomcat');
    cy.get(generatorSelector('add-spring-boot-webflux-netty-button')).contains('Spring Webflux Netty');
    cy.get(generatorSelector('add-spring-boot-actuator-button')).contains('Spring Boot Actuator');

    cy.get(generatorSelector('add-spring-boot-aop-button')).contains('AOP Logging');
    cy.get(generatorSelector('add-spring-boot-logstash-button')).contains('Logstash');

    cy.get(generatorSelector('add-spring-boot-jwt-button')).contains('Security JWT');
    cy.get(generatorSelector('add-spring-boot-jwt-basic-auth-button')).contains('Security JWT Basic Auth');

    cy.get(generatorSelector('add-spring-boot-database-postgresql-button')).contains('PostgreSQL');
    cy.get(generatorSelector('add-spring-boot-database-mysql-button')).contains('MySQL');
    cy.get(generatorSelector('add-spring-boot-database-mariadb-button')).contains('MariaDB');
    cy.get(generatorSelector('add-spring-boot-database-mongodb-button')).contains('MongoDB');
  });

  it('should display angular', () => {
    cy.get(generatorSelector('option-angular')).check();
    cy.get(angularGeneratorSelector('add-angular-button')).contains('Angular');
  });

  it('should display react', () => {
    cy.get(generatorSelector('option-react')).check();
    cy.get(generatorSelector('add-react-button')).contains('React');
  });

  it('should display vue', () => {
    cy.get(generatorSelector('option-vue')).check();
    cy.get(generatorSelector('add-vue-button')).contains('Vue');
  });

  it('should display download button when project path is filled', () => {
    cy.get('#path').type('/tmp/jhlite');
    cy.get(generatorSelector('download-button')).contains('Download');
  });
});
