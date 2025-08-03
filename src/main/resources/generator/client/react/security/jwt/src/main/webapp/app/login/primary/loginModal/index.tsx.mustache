import { Button, Input, Modal, ModalBody, ModalContent, ModalHeader, Spacer } from '@heroui/react';
import { useCallback, useContext, useState } from 'react';
import { useForm } from 'react-hook-form';

import { EyeFilledIcon } from './EyeFilledIcon';
import { EyeSlashFilledIcon } from './EyeSlashFilledIcon';

import { UserInfoContext } from '@/login/primary/loginForm';
import { login } from '@/login/services/login';

import './LoginModal.scss';

const LoginModal = ({ open, onClose }: LoginModalType) => {
  const { register, handleSubmit } = useForm<LoginFunctionType>();
  const [error, setError] = useState(false);
  const [isVisible, setIsVisible] = useState(false);
  const { setUsername, setToken } = useContext(UserInfoContext);

  const toggleVisibility = () => setIsVisible(!isVisible);

  const onSubmit = async (loginData: LoginFunctionType) => {
    if (loginData.username && loginData.password) {
      await login({ ...loginData, setUsername, setToken });
      setError(false);
      onClose();
    } else {
      setError(true);
    }
  };

  const handleClose = useCallback(() => {
    setError(false);
    onClose();
  }, []);

  return (
    <Modal backdrop="blur" isOpen={open} onClose={handleClose} aria-labelledby="modal-login">
      <ModalContent>
        <ModalHeader>
          <span>Connect</span>
          <Spacer x={1} />
          <span>Seed4J</span>
        </ModalHeader>
        <ModalBody>
          <form onSubmit={handleSubmit(onSubmit)}>
            <Input
              isClearable
              fullWidth
              aria-label="modal-login-username"
              data-testid="modal-login-username"
              color="primary"
              size="lg"
              placeholder="Username"
              type="text"
              {...register('username')}
            />
            <Spacer y={1} />
            <Input
              fullWidth
              aria-label="modal-login-password"
              data-testid="modal-login-password"
              color="primary"
              size="lg"
              placeholder="Password"
              type={isVisible ? 'text' : 'password'}
              endContent={
                <button data-testid="display-password" className="focus:outline-none" type="button" onClick={toggleVisibility}>
                  {isVisible ? (
                    <EyeSlashFilledIcon className="text-2xl text-default-400 pointer-events-none" />
                  ) : (
                    <EyeFilledIcon className="text-2xl text-default-400 pointer-events-none" />
                  )}
                </button>
              }
              {...register('password')}
            />
            <Spacer y={1} />
            {error && (
              <p data-testid="error-message" color="error">
                Please complete the fields above
              </p>
            )}
            <Spacer y={1} />
            <div className="flex gap-2">
              <input type="checkbox" {...register('rememberMe')} />
              <span>Remember me</span>
            </div>
            <Spacer y={1} />
            <Button data-testid="submit-button" className="submit-button" type="submit" variant="shadow">
              Log in
            </Button>
          </form>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default LoginModal;
