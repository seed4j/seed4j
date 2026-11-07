import type { Page, Route } from '@playwright/test';
import path from 'node:path';

type Response = Parameters<Route['fulfill']>[0];

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
 * Builds a response with the content of a file of the fixtures folder.
 * @param name - The name of the fixture file.
 * @returns The response to fulfill a route with.
 */
export const fixture = (name: string): Response => ({ path: path.resolve(import.meta.dirname, '../fixtures', name) });

/**
 * Intercepts a request indefinitely until `send` is called.
 * @param page - The page to intercept the request in.
 * @param url - The glob pattern of the request URL.
 * @param response - The response to send when resolved.
 * @returns An object with a `send` method to trigger the response.
 */
export const interceptForever = async (page: Page, url: string, response: Response): Promise<ResponseSender> => {
  const [deferredPromise, resolveDeferredPromise] = createDeferredPromise();

  await page.route(url, async route => {
    await deferredPromise;
    await route.fulfill(response);
  });

  return { send: resolveDeferredPromise };
};
