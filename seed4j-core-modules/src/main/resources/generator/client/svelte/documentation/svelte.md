# Svelte

The application uses [SvelteKit](https://svelte.dev/docs/kit) as a single-page application: pages are rendered in the browser (`ssr = false` in `routes/+layout.ts`) and the static build is served by the backend.

## Routes

Files in `src/main/webapp/routes` are entry points: they only display a component. The application code lives in `src/main/webapp/app`, organized by context, and can be imported with the `@/` alias.

Routes are not included in the code coverage, so keep them free of logic.

## Internal links

Internal links have to go through `resolve()` from `$app/paths`, or the linter will reject them:

```svelte
<script lang="ts">
  import { resolve } from '$app/paths';
</script>

<a href={resolve('/about')}>About</a>
```

## Code coverage

A value mixed with text in the markup, like `Hello {name}`, compiles to `name ?? ''`. Tests can't reach the empty branch when the value is never nullish, which fails the 100% coverage threshold.

Compute the text in the script instead. A value alone in its tag (`{name}`), `{#if}` and `{#each}` are not concerned.

```svelte
<script lang="ts">
  const { name }: { name: string } = $props();
  const greeting = $derived(`Hello ${name}`);
</script>

<h1>{greeting}</h1>
```

## Other modules

These modules can be applied on a Svelte application, but they are not verified with it:

- `tikui`: its stylesheet link targets `src/main/webapp/index.html`, which doesn't exist with SvelteKit. The module applies without its styles, add the link in `src/main/webapp/app.html`.
- `cypress-component-tests`, `cypress-e2e` and `cypress-merge-coverage`: these modules are not tested on Svelte.
