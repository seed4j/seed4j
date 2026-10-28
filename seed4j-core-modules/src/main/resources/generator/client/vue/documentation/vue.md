# Vue

We encourage you to use `defineComponent` to make the logic of your component.

## Props

The _props_ cannot natively be typed with TypeScript with `defineComponent`:

```typescript
export default defineComponent({
  props: {
    stringExample: {
      type: String,
      required: true,
    },
  },
  // …
});
```

Here the _prop_ `stringExample` is typed from the JavaScript `String` object and not from the `string` type.

Sometimes we want to have more complex types like an _array_ of _string_ or a more complex _object_.

That's why we've added `arrayType` and `objectType` utilities from `@/vue/VueProp`.

Example for a prop `arrayOfStrings` using `string[]` type:

```typescript
import { arrayType } from '@/vue/VueProp';

export default defineComponent({
  props: {
    arrayOfStrings: {
      type: arrayType<string>(),
      required: true,
    },
  },
  // …
});
```

Example for a prop `customObject` using `CustomObject` type:

```typescript
import { objectType } from '@/vue/VueProp';

interface CustomObject {
  stringField: string;
  numberField: number;
}

export default defineComponent({
  props: {
    customObject: {
      type: objectType<CustomObject>(),
      required: true,
    },
  },
  // …
});
```

With the two functions, you're now able to type and get the good types when you inject your props from the setup:

```typescript
import { arrayType, objectType } from '@/vue/VueProp';

interface CustomObject {
  stringField: string;
  numberField: number;
}

export default defineComponent({
  props: {
    customObject: {
      type: objectType<CustomObject>(),
      required: true,
    },
    arrayOfStrings: {
      type: arrayType<string>(),
      required: true,
    },
  },
  setup(props) {
    props.customObject; // Typed as CustomObject
    props.arrayOfStrings; // Typed as string[]
  },
});
```
