import axios from 'axios';

import { setLocalStorage } from '@/common/services/storage';

const LOGIN_URL = '/api/authenticate';

export const login = ({ username, password, rememberMe, setUsername, setToken }: LoginFunctionType) => {
  return axios
    .post(LOGIN_URL, {
      username,
      password,
      rememberMe,
    })
    .then((response: { data: { id_token: string } }) => {
      setLocalStorage('token', response.data.id_token);
      setLocalStorage('username', username);
      setUsername(username);
      setToken(response.data.id_token);
    })
    .catch(error => {
      console.error(error);
    });
};
