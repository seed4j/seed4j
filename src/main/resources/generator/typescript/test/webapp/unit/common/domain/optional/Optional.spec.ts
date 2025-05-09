import { expect, it } from 'vitest';

import { Optional } from '@/common/domain/Optional';

describe('Optional', () => {
  describe('Empty check', () => {
    it('should be empty for empty optional', () => {
      expect(Optional.empty().isEmpty()).toBe(true);
    });

    it('should not be empty for valuated optional', () => {
      expect(Optional.of('beer').isEmpty()).toBe(false);
    });
  });

  describe('Is present', () => {
    it('should not be present for empty optional', () => {
      expect(Optional.empty().isPresent()).toBe(false);
    });

    it('should be present for valuated optional', () => {
      expect(Optional.of('value').isPresent()).toBe(true);
    });
  });

  describe('Map', () => {
    it('should map empty optional to empty', () => {
      expect(
        Optional.empty()
          .map(dummy => dummy)
          .isEmpty(),
      ).toBe(true);
    });

    it('should map valuated optional', () => {
      expect(
        Optional.of('value')
          .map(value => 'beer ' + value)
          .orElse('dummy'),
      ).toBe('beer value');
    });
  });

  describe('Filter', () => {
    it('should filter empty optional to empty', () => {
      expect(
        Optional.empty()
          .filter(() => true)
          .isEmpty(),
      ).toBe(true);
    });

    it('should filter valuated optional with matching filter', () => {
      expect(
        Optional.of('value')
          .filter(value => value.indexOf('v') !== -1)
          .orElse('dummy'),
      ).toBe('value');
    });

    it('should filter valued optional with non-matching filter', () => {
      expect(
        Optional.of('value')
          .filter(() => false)
          .orElse('other'),
      ).toBe('other');
    });
  });

  describe('Or', () => {
    it('should get alternative from empty optional', () => {
      expect(Optional.empty().or(() => Optional.of('beer'))).toStrictEqual(Optional.of('beer'));
    });

    it('should get initial value from not empty optional', () => {
      expect(Optional.of('cheese').or(() => Optional.of('beer'))).toStrictEqual(Optional.of('cheese'));
    });
  });

  describe('Or else', () => {
    it('should get alternative from empty optional', () => {
      expect(Optional.empty().orElse('beer')).toBe('beer');
    });
  });

  describe('Or else get', () => {
    it('should get alternative from empty optional', () => {
      expect(Optional.empty().orElseGet(() => 'beer')).toBe('beer');
    });

    it('should get initial value from not empty optional', () => {
      expect(Optional.of('cheese').orElseGet(() => 'beer')).toBe('cheese');
    });
  });

  describe('Or else throw', () => {
    it('should throw error for empty optional', () => {
      expect(() => Optional.empty().orElseThrow()).toThrowError();
    });

    it('should get value for valuated optional', () => {
      expect(Optional.of('value').orElseThrow()).toBe('value');
    });

    it('should get supplied error when present', () => {
      expect(() => Optional.empty().orElseThrow(() => new Error('Supplied error'))).toThrow(new Error('Supplied error'));
    });
  });

  describe('Of undefinable', () => {
    it.each([undefined, null, NaN])('should get empty optional from %s value', value => {
      expect(Optional.ofNullable(value).isEmpty()).toBe(true);
    });

    it('should get valuated optional from actual value', () => {
      expect(Optional.ofNullable('toto').isEmpty()).toBe(false);
    });
  });

  describe('Flat map', () => {
    it('should flat map empty optional to empty', () => {
      expect(Optional.empty().flatMap(Optional.empty).isEmpty()).toBe(true);
    });

    it('should flat map valuated optional to empty when mapping to empty', () => {
      expect(Optional.of('beer').flatMap(Optional.empty).isEmpty()).toBe(true);
    });

    it('should flat map valuated optional to new value', () => {
      expect(
        Optional.of('beer')
          .flatMap(value => Optional.of('my ' + value))
          .orElse('dummy'),
      ).toBe('my beer');
    });
  });

  describe('If present', () => {
    it('should not consume value on empty optional', () => {
      let value = 42;

      Optional.empty().ifPresent(() => value++);

      expect(value).toBe(42);
    });

    it('should consume value on valuated optional', () => {
      let value = 42;

      Optional.of(12).ifPresent(v => (value = value + v));

      expect(value).toBe(42 + 12);
    });
  });

  describe('To array', () => {
    it('should get empty array from empty optional', () => {
      expect(Optional.empty().toArray()).toEqual([]);
    });

    it('should get array with value for valuated optional', () => {
      expect(Optional.of('value').toArray()).toEqual(['value']);
    });
  });
});
