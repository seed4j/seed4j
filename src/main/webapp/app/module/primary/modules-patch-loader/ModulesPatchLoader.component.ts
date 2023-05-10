import { defineComponent } from 'vue';

export default defineComponent({
  name: 'ModulesPatchLoaderVue',
  setup() {
    return {
      rows: [createCategory(4), createCategory(1), createCategory(3), createCategory(1), createCategory(2), createCategory(2)],
    };
  },
});

const createCategory = (modulesCount: number) => {
  const baseColumns = [createBigColumn(2), createBigColumn(10, true), createNormalEmptyColumn(12), createBigColumn(12)];
  const additionalColumns = Array(modulesCount - 1).fill(createBigColumn(12));
  const lastColumn = createBigColumn(12, true);
  return createRow(...baseColumns, ...additionalColumns, lastColumn);
};

const createColumn = (size: number, classes: string) => ({ size, classes });

const createBigColumn = (size: number, isEmpty: boolean = false) => createColumn(size, `${isEmpty ? 'empty ' : ''}big`);

const createNormalEmptyColumn = (size: number) => createColumn(size, 'empty');

const createRow = (...columns: any[]) => ({ columns });
