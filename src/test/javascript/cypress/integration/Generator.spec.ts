import { composeSelector, dataSelector } from '../support/selector';

const generatorSelector = (name: string) => dataSelector(composeSelector('generator', name));
const headerSelector = (name: string) => dataSelector(composeSelector('header', name));
const springBootGeneratorSelector = (name: string) => dataSelector(composeSelector('spring-boot-generator', name));
const angularGeneratorSelector = (name: string) => dataSelector(composeSelector('angular-generator', name));
const reactGeneratorSelector = (name: string) => dataSelector(composeSelector('react-generator', name));
const vueGeneratorSelector = (name: string) => dataSelector(composeSelector('vue-generator', name));
const svelteGeneratorSelector = (name: string) => dataSelector(composeSelector('svelte-generator', name));

describe('Generator', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should redirect to generator by default', () => {
    cy.url().should('include', '/generator');
  });

  it('should display generator page', () => {
    cy.get(headerSelector('title')).contains('JHipster lite');

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
    cy.get(springBootGeneratorSelector('add-spring-boot-button')).contains('Spring Boot');
    cy.get(springBootGeneratorSelector('add-spring-boot-mvc-tomcat-button')).contains('Spring MVC Tomcat');
    cy.get(springBootGeneratorSelector('add-spring-boot-webflux-netty-button')).contains('Spring Webflux Netty');
    cy.get(springBootGeneratorSelector('add-spring-boot-actuator-button')).contains('Spring Boot Actuator');

    cy.get(springBootGeneratorSelector('add-spring-boot-aop-button')).contains('AOP Logging');
    cy.get(springBootGeneratorSelector('add-spring-boot-logstash-button')).contains('Logstash');

    cy.get(springBootGeneratorSelector('add-spring-boot-jwt-button')).contains('Security JWT');
    cy.get(springBootGeneratorSelector('add-spring-boot-jwt-basic-auth-button')).contains('Security JWT Basic Auth');

    cy.get(springBootGeneratorSelector('add-spring-boot-database-postgresql-button')).contains('PostgreSQL');
    cy.get(springBootGeneratorSelector('add-spring-boot-database-mysql-button')).contains('MySQL');
    cy.get(springBootGeneratorSelector('add-spring-boot-database-mariadb-button')).contains('MariaDB');
    cy.get(springBootGeneratorSelector('add-spring-boot-database-mongodb-button')).contains('MongoDB');
  });

  it('should display angular', () => {
    cy.get(generatorSelector('option-angular')).check();
    cy.get(angularGeneratorSelector('add-angular-button')).contains('Angular');
  });

  it('should display react', () => {
    cy.get(generatorSelector('option-react')).check();
    cy.get(reactGeneratorSelector('add-react-button')).contains('React');
  });

  it('should display vue', () => {
    cy.get(generatorSelector('option-vue')).check();
    cy.get(vueGeneratorSelector('add-vue-button')).contains('Vue');
  });

  it('should display svelte', () => {
    cy.get(generatorSelector('option-svelte')).check();
    cy.get(svelteGeneratorSelector('add-svelte-button')).contains('Svelte');
  });

  it('should display download button when project path is filled', () => {
    cy.get('#path').type('/tmp/jhlite');
    cy.get(generatorSelector('download-button')).contains('Download');
  });
});
