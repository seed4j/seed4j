import { createContext, useState, useMemo, useCallback } from 'react';
import { Button } from '@nextui-org/react';

import LoginModal from '@/login/primary/loginModal';

export const UserInfoContext = createContext<UserInfoContextType | Record<string, never>>({});

const LoginForm = () => {
  const [isOpen, setOpen] = useState(false);
  const [username, setUsername] = useState<any>('');
  const [token, setToken] = useState<any>('');

  const userInfoContextValues = useMemo(
    () => ({
      setUsername,
      setToken,
    }),
    []
  );

  const onClickLoginButton = useCallback(() => setOpen(true), []);

  const onCloseModal = useCallback(() => setOpen(false), []);

  return (
    <div>
      <Button css={{ backgroundColor: '$blue700' }} auto shadow onClick={onClickLoginButton}>
        Log in
      </Button>
      {token && (
        <p>
          Welcome <strong>{username}</strong> !
        </p>
      )}
      <UserInfoContext.Provider value={userInfoContextValues}>
        <LoginModal open={isOpen} onClose={onCloseModal} />
      </UserInfoContext.Provider>
    </div>
  );
};

export default LoginForm;
