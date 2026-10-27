import { LandscapeElementId } from '@/module/domain/landscape/LandscapeElementId';

const SPACER_SIZE = 9;
const CURVE_RADIUS = 6;

const MOVE_TO_COMMAND = 'M';
const LINE_TO_COMMAND = 'L';
const CURVE_TO_COMMAND = 'C';

export class LandscapeConnector {
  readonly points: string;
  readonly path: string;
  constructor(
    readonly positions: LandscapeConnectorPosition[],
    readonly startingElement: LandscapeElementId,
    readonly endingElement: LandscapeElementId,
  ) {
    this.points = this.buildPoints();
    this.path = this.buildCurvedPath();
  }

  private buildPoints(): string {
    return this.positions.map(position => position.x + ' ' + position.y).join(' ');
  }

  private buildCurvedPath(): string {
    const commands: string[] = [];
    commands.push(this.buildCommand(MOVE_TO_COMMAND, [this.positions[0]]));

    const firstControlPoint: LandscapeConnectorPosition = this.positions[1];
    const secondControlPoint: LandscapeConnectorPosition = this.positions[2];

    const firstCurveStartPosition: LandscapeConnectorPosition = {
      x: firstControlPoint.x - CURVE_RADIUS,
      y: firstControlPoint.y,
    };
    const firstCurveEndPosition: LandscapeConnectorPosition = {
      x: firstControlPoint.x,
      y: firstControlPoint.y > secondControlPoint.y ? firstControlPoint.y - CURVE_RADIUS : firstControlPoint.y + CURVE_RADIUS,
    };
    commands.push(
      this.buildCommand(LINE_TO_COMMAND, [firstCurveStartPosition]),
      this.buildCommand(CURVE_TO_COMMAND, [firstCurveStartPosition, firstControlPoint, firstCurveEndPosition]),
    );

    const secondCurveStartPosition: LandscapeConnectorPosition = {
      x: secondControlPoint.x,
      y: firstControlPoint.y > secondControlPoint.y ? secondControlPoint.y + CURVE_RADIUS : secondControlPoint.y - CURVE_RADIUS,
    };
    const secondCurveEndPosition: LandscapeConnectorPosition = {
      x: secondControlPoint.x + CURVE_RADIUS,
      y: secondControlPoint.y,
    };
    commands.push(
      this.buildCommand(LINE_TO_COMMAND, [secondCurveStartPosition]),
      this.buildCommand(CURVE_TO_COMMAND, [secondCurveStartPosition, secondControlPoint, secondCurveEndPosition]),
      this.buildCommand(LINE_TO_COMMAND, [this.positions.at(-1)!]),
    );

    return commands.join(' ');
  }

  private buildCommand(command: string, positions: LandscapeConnectorPosition[]): string {
    return command + ' ' + positions.map(position => position.x + ' ' + position.y).join(' ');
  }
}

export interface LandscapeConnectorPosition {
  x: number;
  y: number;
}

interface BuildConnectorsParameters {
  dependencyElementSlug: LandscapeElementId;
  dependantElementSlug: LandscapeElementId;
  dependencyElement: HTMLElement;
  dependantElement: HTMLElement;
}

export const buildConnector = (parameters: BuildConnectorsParameters): LandscapeConnector => {
  const { dependencyElement, dependantElement } = parameters;

  const dependencyElementX = dependencyElement.offsetLeft + dependencyElement.offsetWidth;
  const dependencyElementY = Math.round(dependencyElement.offsetTop + dependencyElement.offsetHeight / 2);

  const dependantElementX = dependantElement.offsetLeft;
  const dependantElementY = Math.round(dependantElement.offsetTop + dependantElement.offsetHeight / 2);

  const dependencyStartingPoint: LandscapeConnectorPosition = {
    x: dependencyElementX,
    y: dependencyElementY,
  };

  const dependencySpacer: LandscapeConnectorPosition = {
    x: dependencyElementX + SPACER_SIZE,
    y: dependencyElementY,
  };

  const dependantSpacer: LandscapeConnectorPosition = {
    x: dependencyElementX + SPACER_SIZE,
    y: dependantElementY,
  };

  const dependantStartingPoint: LandscapeConnectorPosition = {
    x: dependantElementX,
    y: dependantElementY,
  };

  return new LandscapeConnector(
    [dependencyStartingPoint, dependencySpacer, dependantSpacer, dependantStartingPoint],
    parameters.dependantElementSlug,
    parameters.dependencyElementSlug,
  );
};
