import { Button } from '@heroui/react';
import { createContext, useCallback, useMemo, useState } from 'react';

import LoginModal from '@/login/primary/loginModal';

export const UserInfoContext = createContext<UserInfoContextType | Record<string, never>>({});

const LoginForm = () => {
  const [open, setOpen] = useState(false);
  const [username, setUsername] = useState('');
  const [token, setToken] = useState('');

  const userInfoContextValues = useMemo(
    () => ({
      setUsername,
      setToken,
    }),
    [],
  );

  const onPressLoginButton = useCallback(() => setOpen(true), []);

  const onPressLogoutButton = useCallback(() => setToken(''), []);

  const onCloseModal = useCallback(() => setOpen(false), []);

  return (
    <div>
      {token ? (
        <>
          <p>
            Welcome <strong>{username}</strong>!
          </p>
          <Button variant="shadow" onPress={onPressLogoutButton}>
            Log out
          </Button>
        </>
      ) : (
        <Button data-testid="login-button" variant="shadow" onPress={onPressLoginButton}>
          Log in
        </Button>
      )}
      <UserInfoContext.Provider value={userInfoContextValues}>
        <LoginModal open={open} onClose={onCloseModal} />
      </UserInfoContext.Provider>
    </div>
  );
};

export default LoginForm;
