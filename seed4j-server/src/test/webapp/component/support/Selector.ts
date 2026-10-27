export const dataSelector = (selector: string): string =>
  `[data-selector="${selector}"], [data-cy="${selector}"], [data-test="${selector}"], [data-testid="${selector}"]`;
