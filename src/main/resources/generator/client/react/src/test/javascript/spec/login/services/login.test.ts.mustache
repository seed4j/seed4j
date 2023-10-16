import axios from 'axios';
import { describe, it, expect, vi } from 'vitest';

import { login } from '@/login/services/login';

const data = {
  username: 'test',
  password: 'test',
  rememberMe: true,
};

const idToken = 123;

describe('login function', () => {
  it('should return a promise', () => {
    const spy = vi.spyOn(axios, 'post');
    spy.mockImplementationOnce(() => Promise.reject(new Error('error')));
    const setUsername = vi.fn();
    const setToken = vi.fn();
    expect(login({...data, setToken, setUsername})).toBeInstanceOf(Promise);
  });

  it('should set token and username', async () => {
    const spy = vi.spyOn(axios, 'post');
    spy.mockImplementationOnce(() => Promise.resolve({ data: { id_token: idToken } }));
    const setUsername = vi.fn();
    const setToken = vi.fn();
    await login({...data, setToken, setUsername});
    expect(setUsername).toHaveBeenCalledWith(data.username);
    expect(setToken).toHaveBeenCalledWith(idToken);
  });
});
