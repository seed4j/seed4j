export const wrappedElement = (selector: string) =>
  `[data-selector="${selector}"], [data-cy="${selector}"], [data-test="${selector}"], [data-testid="${selector}"]`;
