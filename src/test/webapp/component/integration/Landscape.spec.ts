import { interceptForever } from '../support/Interceptor';
import { dataSelector } from '../support/Selector';

describe('Landscape', () => {
  beforeEach(() => cy.intercept({ path: '/api/project-folders' }, { body: '/tmp/seed4j/1234' }));

  it('should change theme after toggle switch theme button', () => {
    cy.visit('/landscape', {
      // see https://www.cypress.io/blog/2019/12/13/test-your-web-app-in-dark-mode/
      onBeforeLoad(win) {
        cy.stub(win, 'matchMedia').withArgs('(prefers-color-scheme: dark)').returns({ matches: true });
      },
    });

    const themeSwitchButton = dataSelector('theme-switch-button');

    cy.get(themeSwitchButton).should('exist').should('not.be.visible').should('not.be.checked');
    cy.get('[aria-label="dark-theme"]').should('exist');

    cy.get(themeSwitchButton).click({ force: true });
    cy.get(themeSwitchButton).should('be.checked');
    cy.get('[aria-label="light-theme"]').should('exist');

    cy.get(themeSwitchButton).click({ force: true });
    cy.get(themeSwitchButton).should('not.be.checked');
    cy.get('[aria-label="dark-theme"]').should('exist');
  });

  it('should display landscape as default page', () => {
    cy.intercept({ path: '/api/modules-landscape' }, { fixture: 'landscape.json' });
    cy.visit('/');

    cy.url().should('include', '/landscape');
  });

  it('should apply modules using WebServices', () => {
    const result = interceptForever({ path: '/api/modules-landscape' }, { fixture: 'landscape.json' });

    cy.intercept({
      path: '/api/apply-patches',
      method: 'POST',
    }).as('modules-application');

    cy.visit('/landscape');

    cy.get(dataSelector('landscape-loader'))
      .should('be.visible')
      .then(() => {
        result.send();
        cy.get(dataSelector('landscape-loader')).should('not.exist');

        cy.get(dataSelector('init-module')).click();
        cy.get(dataSelector('parameter-packageName-field')).type('value');
        cy.get(dataSelector('modules-apply-all-button')).click();

        cy.wait('@modules-application').should(xhr => {
          const body = xhr.request.body;

          expect(body).to.deep.equal({
            modules: ['init'],
            properties: {
              projectFolder: '/tmp/seed4j/1234',
              commit: true,
              parameters: {
                packageName: 'value',
              },
            },
          });
        });
      });
  });
});
