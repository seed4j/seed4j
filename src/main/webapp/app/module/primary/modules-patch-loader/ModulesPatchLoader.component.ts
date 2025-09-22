import { defineComponent } from 'vue';

export default defineComponent({
  name: 'ModulesPatchLoaderVue',
  setup() {
    return {
      rows: [createCategory(4), createCategory(1), createCategory(3), createCategory(1), createCategory(2), createCategory(2)],
    };
  },
});

interface Column {
  size: number;
  classes: string;
}

interface Row {
  columns: Column[];
}

const createCategory = (modulesCount: number): Row => {
  const baseColumns = [createBigColumn(2), createBigColumn(10, true), createNormalEmptyColumn(12), createBigColumn(12)];
  const additionalColumns = new Array(modulesCount - 1).fill(createBigColumn(12));
  const lastColumn = createBigColumn(12, true);
  return createRow(...baseColumns, ...additionalColumns, lastColumn);
};

const createColumn = (size: number, classes: string): Column => ({ size, classes });

const createBigColumn = (size: number, isEmpty: boolean = false): Column => createColumn(size, `${isEmpty ? 'empty ' : ''}big`);

const createNormalEmptyColumn = (size: number): Column => createColumn(size, 'empty');

const createRow = (...columns: Column[]): Row => ({ columns });
