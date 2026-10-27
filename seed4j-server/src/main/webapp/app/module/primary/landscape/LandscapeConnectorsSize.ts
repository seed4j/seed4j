export interface LandscapeConnectorsSize {
  width: number;
  height: number;
}

export const emptyLandscapeSize = (): LandscapeConnectorsSize => ({
  width: 0,
  height: 0,
});
