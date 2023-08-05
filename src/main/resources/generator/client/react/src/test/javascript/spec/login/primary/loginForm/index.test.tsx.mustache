import axios from 'axios';
import { vi, describe, it, expect } from 'vitest';

import LoginForm from '@/login/primary/loginForm/index';
import { act, fireEvent, render } from '@testing-library/react';

const mockPost = () => {
  const spy = vi.spyOn(axios, 'post');
  spy.mockImplementationOnce(() => Promise.resolve({ data: { id_token: 123 } }));
  return spy;
};

const login = async () => {
  const { getByText, getByPlaceholderText, getByTestId } = render(<LoginForm/>);
  const loginButton = getByText('Log in');
    fireEvent.click(loginButton);
    await act(async () => {
      fireEvent.change(getByPlaceholderText("Nom d'utilisateur"), {
        target: {value: 'admin'},
      });
      fireEvent.change(getByPlaceholderText('Mot de passe'), {
        target: {value: 'admin'},
      });
      const submitButton = getByTestId('submit-button');
      fireEvent.click(submitButton);
  });
  return { getByText, getByPlaceholderText, getByTestId };
};

describe('loginForm', () => {
  it('should render the login button without crashing', () => {
    render(<LoginForm/>);
  });

  it('render button should contain "Log in"', () => {
    const {getByText} = render(<LoginForm/>);
    const loginButton = getByText('Log in');
    expect(loginButton).toBeTruthy();
  });

  it('render the modal on login button click and close it', async () => {
    const {getByText, getByTestId} = render(<LoginForm/>);
    const loginButton = getByText('Log in');
    fireEvent.click(loginButton);
    expect(getByText('Connect')).toBeTruthy();
    const submitButton = getByTestId('submit-button');
    await act(async () => {
      fireEvent.click(submitButton);
    });
  });

  it('should close when clicking submit button with complete fields', async () => {
    const spy = mockPost();
    await login();

    expect(spy).toHaveBeenCalledTimes(1);
  });

  it('should display Log out after login and display Log in after logout', async () => {
    mockPost();
    const { getByText, getByTestId } = await login();

    expect(getByText('Log out')).toBeTruthy();
    const logoutButton = getByText('Log out');
    fireEvent.click(logoutButton);
    expect(getByTestId('login-button')).toBeTruthy();
  });
});
