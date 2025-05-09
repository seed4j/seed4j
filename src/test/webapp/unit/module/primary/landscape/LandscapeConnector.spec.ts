import { describe, expect, it } from 'vitest';

import { LandscapeConnector, LandscapeConnectorPosition } from '@/module/primary/landscape/LandscapeConnector';

import { moduleSlug } from '../../domain/Modules.fixture';

describe('LandscapeConnector', () => {
  describe('Generate curved path', () => {
    it('should generate curved path for bottom-right dependant', () => {
      const positions: LandscapeConnectorPosition[] = [
        {
          x: 0,
          y: 0,
        },
        {
          x: 10,
          y: 0,
        },
        {
          x: 10,
          y: 100,
        },
        {
          x: 100,
          y: 100,
        },
      ];
      const landscapeConnector: LandscapeConnector = new LandscapeConnector(positions, moduleSlug('moduleA'), moduleSlug('B'));
      //MoveTo 'startPoint', LineTo, CurveTo(3 points), LineTo, CurveTo(3 points), LineTo 'endPoint'
      expect(landscapeConnector.path).toBe('M 0 0 L 4 0 C 4 0 10 0 10 6 L 10 94 C 10 94 10 100 16 100 L 100 100');
    });

    it('should generate curved path for top-right dependant', () => {
      const positions: LandscapeConnectorPosition[] = [
        {
          x: 100,
          y: 100,
        },
        {
          x: 110,
          y: 100,
        },
        {
          x: 110,
          y: 0,
        },
        {
          x: 200,
          y: 0,
        },
      ];
      const landscapeConnector: LandscapeConnector = new LandscapeConnector(positions, moduleSlug('moduleA'), moduleSlug('B'));
      //MoveTo 'startPoint', LineTo, CurveTo(3 points), LineTo, CurveTo(3 points), LineTo 'endPoint'
      expect(landscapeConnector.path).toBe('M 100 100 L 104 100 C 104 100 110 100 110 94 L 110 6 C 110 6 110 0 116 0 L 200 0');
    });
  });
});
