import { TestBed } from '@angular/core/testing';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { AuthServerProvider } from './auth-jwt.service';
import { AuthInterceptor } from './auth.interceptor';

describe('Auth Interceptor', () => {
  let service: AuthServerProvider;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        provideHttpClient(
          withInterceptors([AuthInterceptor])
        ),
        provideHttpClientTesting(),
      ],
    });

    httpMock = TestBed.inject(HttpTestingController);
    service = TestBed.inject(AuthServerProvider);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('intercept', () => {
    it('should add authorization header with localStorageService', () => {
      // GIVEN
      const token = 'azerty';
      Storage.prototype.getItem = jest.fn(() => token);

      // WHEN
      service.login({ username: 'John', password: '123' }).subscribe();

      // THEN
      const httpReq = httpMock.expectOne('api/authenticate');
      expect(httpReq.request.headers.has('Authorization')).toBe(true);
    });
  });
});
