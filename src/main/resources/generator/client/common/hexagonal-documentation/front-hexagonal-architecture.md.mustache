# Hexagonal architecture in front

There are many ways to implement Hexagonal Architecture in front applications. The way described here is, by no means,
THE WAY. It is just a way, feel free to adapt it to your needs.

This documentation is not an introduction to hexagonal architecture. If you want to learn more about it, you can read
[this page](https://github.com/seed4j/seed4j/blob/main/documentation/hexagonal-architecture.md).

## Folder structure

in `src/main/webapp/app` root folder represents the bounded contexts. When starting something new, you won't know what
Bounded Context you're in. Don't spend too much time on that: create a folder, and you'll rename / move it later.

Let's dive into a Bounded Context (example in vue.js, but it's the same for other frontends frameworks):

```bash
├── brewery
│   ├── application
│   │   ├── BreweryKeys.ts
│   │   ├── BreweryProvider.ts
│   │   └── BreweryRouter.ts
│   ├── domain
│   │   ├── BreweryName.ts
│   │   ├── BreweryRepository.ts
│   │   ├── Brewery.ts
│   │   └── BreweryType.ts
│   └── infrastructure
│       ├── primary
│           ├── BreweryVue.vue
│       └── secondary
│           ├── RestBreweryRepository.ts
│           └── RestBrewery.ts

```

In `application` we have the wiring points in the application, we'll come back to that later.
In `domain` we have the Domain Model, it's the heart of the application, the code that really matters. In the frontend,
the Domain Model represents the current state of the application.
In `infrastructure/primary` we basically find the UI, it's the code to display elements to the user.
In `infrastructure/secondary` we find the code used to communicate other elements of the solution (call the backend,
sending messages, etc.).

Some Bounded Contexts are shared between multiple Bounded Contexts, in that case, they are in `src/main/webapp/app/shared` and follow the
same structure.

## Handling injections

Hexagonal Architecture is about dependencies inversion. For that, you'll need Dependency Injection (DI).

There are many ways to do this, we like using [piqure](https://github.com/Gnuk/piqure). It's a 43 line library allowing
typesafe injections.

For each element you want to inject, you'll need a key:

```typescript
import { key } from 'piqure';
import { BreweryRepository } from '@/home/domain/BreweryRepository.ts';

export const BREWERY_REPOSITORY = key<BreweryRepository>('BreweryRepository');
```

You'll then be able to provide an implementation:

```typescript
import { AxiosHttp } from '@/shared/rest/infrastructure/secondary/AxiosHttp.ts';
import { BREWERY_REPOSITORY } from '@/home/application/HomeKeys.ts';
import { provide } from '@/injections.ts';
import { RestBreweryRepository } from '@/home/infrastructure/secondary/RestBreweryRepository.ts';

export const provideForHome = (rest: AxiosHttp): void => {
  provide(BREWERY_REPOSITORY, new RestBreweryRepository(rest));
};
```

Here our `BreweryRepository` (and interface in the domain) is implemented by the `RestBreweryRepository` in the
secondary part of the application.

To actually make injections, you'll need to call `provideForHome` in your `main.ts`.

Once you've done that, you can inject dependencies:

```typescript
import { inject } from '@/injections.ts';
import { BREWERY_REPOSITORY } from '@/home/application/HomeKeys.ts';

export default {
  name: 'BreweryVue',
  setup() {
    const breweries = inject(BREWERY_REPOSITORY);

    // TODO: component logic

    return {
      // stuff
    };
  },
};
```

On the frontend, we are injecting ports (in this case, the `BreweryRepository`) in the primary adapters. It is not the
standard way to do it, but it's a totally valid tradeoff: reducing glue and still decoupling infrastructure and domain.

## Handling routes

Bounded Context will have their routes. One way to provide them is to create a `XXXRoutes.ts` file in the `application`:

```typescript
import type { RouteRecordRaw } from 'vue-router';
import HomepageVue from '@/home/infrastructure/primary/HomepageVue.vue';

export const homeRoutes = (): RouteRecordRaw[] => [
  {
    path: '/',
    redirect: { name: 'Homepage' },
  },
  {
    path: '/home',
    name: 'Homepage',
    component: HomepageVue,
  },
];
```

(example for vue.js, need some adaptation for other frontends frameworks)

You can then append these routes to the main routes in `router.ts`:

```typescript
import { createRouter, createWebHistory } from 'vue-router';
import { homeRoutes } from '@/home/application/HomeRouter';

const routes = [...homeRoutes()];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
```
