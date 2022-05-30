import { Login } from '@/common/domain/Login';
import { RestLogin } from '@/common/secondary/RestLogin';
import { User } from '@/common/domain/User';
import AuthenticationRepository from '@/common/secondary/AuthenticationRepository';
import { AxiosHttpStub, stubAxiosHttp } from '../../http/AxiosHttpStub';
import { createPinia, Pinia, setActivePinia, Store } from 'pinia';
import { jwtStore } from '../../../../../main/webapp/app/common/domain/JWTStoreService';

let axiosHttpStub: AxiosHttpStub;
let piniaInstance: Pinia;
let store: Store<any>;

describe('AuthenticationRepository', () => {
  const AUTH_TOKEN =
    'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c';
  beforeEach(() => {
    axiosHttpStub = stubAxiosHttp();
    piniaInstance = createPinia();
    setActivePinia(piniaInstance);
    store = jwtStore(piniaInstance);
  });
  it('Should login when status 200 returned', async () => {
    axiosHttpStub.post.resolves({
      status: 200,
      headers: {
        authorization: 'Bearer ' + AUTH_TOKEN,
      },
    });
    const authenticationRepository = new AuthenticationRepository(axiosHttpStub, piniaInstance);
    const login: Login = { username: 'admin', password: 'admin', rememberMe: true };

    await authenticationRepository.login(login);

    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/authenticate');
    expect(payload).toEqual<RestLogin>({ username: 'admin', password: 'admin', rememberMe: true });
    // @ts-ignore
    expect(store.token).toEqual(AUTH_TOKEN);
  });

  it('Should set empty token', async () => {
    axiosHttpStub.post.resolves({ status: 401, headers: { authorization: '' } });
    const authenticationRepository = new AuthenticationRepository(axiosHttpStub, piniaInstance);
    const login: Login = { username: 'admin', password: 'wrong_password', rememberMe: true };

    await authenticationRepository.login(login);

    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/authenticate');
    expect(payload).toEqual<RestLogin>({ username: 'admin', password: 'wrong_password', rememberMe: true });
    // @ts-ignore
    expect(store.token).toEqual('');
  });

  it('Should authenticate', async () => {
    // @ts-ignore
    store.setToken('fake_token');
    axiosHttpStub.get.resolves({ data: { login: 'username', authorities: ['admin'] } });
    const authenticationRepository = new AuthenticationRepository(axiosHttpStub, piniaInstance);

    const response = await authenticationRepository.authenticate();

    const [uri, payload] = axiosHttpStub.get.getCall(0).args;
    expect(uri).toBe('/api/account');
    expect(payload.headers.Authorization).toEqual('Bearer fake_token');
    expect(response).toStrictEqual<User>({ username: 'username', authorities: ['admin'] });
  });

  it('Should log out', async () => {
    // @ts-ignore
    store.setToken('fake_token');
    const authenticationRepository = new AuthenticationRepository(axiosHttpStub, piniaInstance);

    authenticationRepository.logout();
    // @ts-ignore
    expect(store.token).toEqual('');
  });
});
