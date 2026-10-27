import type { Page } from '@/shared/pagination/domain/Page';
import { type RestPage, toPage } from '@/shared/pagination/infrastructure/secondary/RestPage';
import { describe, expect, it } from 'vitest';

describe('RestPage', () => {
  it('should map empty page', () => {
    expect(
      toPage(mapValue)({
        data: emptyRestPage(),
      }),
    ).toEqual(emptyPage());
  });

  it('should map to page', () => {
    expect(toPage(mapValue)({ data: restPage() })).toEqual(page());
  });
});

const mapValue = (value: string): string => value + value;

const emptyRestPage = () => ({
  currentPage: 0,
  pagesCount: 0,
  pageSize: 1,
  hasPrevious: false,
  hasNext: false,
  totalElementsCount: 0,
  content: [],
});

const emptyPage = () => ({
  currentPage: 1,
  pagesCount: 0,
  hasPrevious: false,
  hasNext: false,
  totalElementsCount: 0,
  displayed: { start: 0, end: 0 },
  content: [],
});

const restPage = (): RestPage<string> => ({
  currentPage: 1,
  pagesCount: 5,
  pageSize: 1,
  hasPrevious: true,
  hasNext: true,
  totalElementsCount: 5,
  content: ['test'],
});

const page = (): Page<string> => ({
  currentPage: 2,
  pagesCount: 5,
  hasPrevious: true,
  hasNext: true,
  totalElementsCount: 5,
  displayed: {
    start: 2,
    end: 2,
  },
  content: ['testtest'],
});
