import { dataSelector } from '../support/Selector';

describe('Patch', () => {
  it('should display modules', () => {
    cy.visit('/patches');

    cy.get(dataSelector('modules-list')).should('be.visible');
  });
});
