import { HttpResponseInterceptor, RouteMatcher, StaticResponse } from 'cypress/types/net-stubbing';

type ResponseSender = {
  send: () => void;
};

const createDeferredPromise = (): [Promise<void>, () => void] => {
  let resolvePromise: () => void = () => {};

  const promise = new Promise<void>(resolve => {
    resolvePromise = resolve;
  });

  return [promise, resolvePromise];
};

/**
 * Intercepts a request indefinitely until `send` is called.
 * @param requestMatcher - The criteria to match the request.
 * @param response - The response to send when resolved.
 * @param alias - An optional alias for the intercepted request.
 * @returns An object with a `send` method to trigger the response.
 */
export const interceptForever = (
  requestMatcher: RouteMatcher,
  response?: StaticResponse | HttpResponseInterceptor,
  alias?: string,
): ResponseSender => {
  const [deferredPromise, resolveDeferredPromise] = createDeferredPromise();

  cy.intercept(requestMatcher, request =>
    deferredPromise.then(() => {
      request.reply(response);
    }),
  ).as(alias || 'request');

  return { send: resolveDeferredPromise };
};
