# Rest Page

Used to map from Rest model to Domain model

Usage:

```typescript
export class MyRepo {
  // constructor

  list(): Promise<Page<DomainObject>> {
    return this.rest.get<RestPage<RestDomainObject>>('/api/domain-object').then(toPage(toDomainObject));
  }
}

type RestDomainObject = {
  id: string;
};

const toDomainObject = (rest: RestDomainObject): DomainObject => ({
  id: rest.id,
});
```
