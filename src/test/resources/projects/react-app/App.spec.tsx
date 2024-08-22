import { render } from '@testing-library/react';
import { describe, it } from 'vitest';

import App from '@/common/primary/app/App';

describe('App tests', () => {
  it('renders without crashing', () => {
    const { getByText } = render(<App />);
    const title = getByText('React + TypeScript + Vite');
    expect(title).toBeTruthy();
  });
});
