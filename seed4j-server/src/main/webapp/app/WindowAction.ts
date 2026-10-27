export type WindowAction = {
  URL: {
    createObjectURL: any;
    revokeObjectURL: any;
  };
  document: {
    createElement: any;
    body: {
      style: {
        cursor: string | undefined;
      };
      appendChild: any;
      removeChild: any;
    };
    documentElement: {
      className: string | undefined;
    };
  };
  matchMedia: any;
};
