import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';

import { Account } from './account.model';

import { AccountService } from './account.service';

enum Authority {
  ADMIN = 'ROLE_ADMIN',
  USER = 'ROLE_USER',
}

function accountWithAuthorities(authorities: string[]): Account {
  return {
    activated: true,
    authorities,
    email: '',
    firstName: '',
    langKey: '',
    lastName: '',
    login: '',
  };
}

describe('Account Service', () => {
  let service: AccountService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideRouter([]), provideHttpClient(), provideHttpClientTesting()],
    });

    service = TestBed.inject(AccountService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('authenticate', () => {
    it('authenticationState should emit null if input is null', () => {
      let userIdentity: Account | null = accountWithAuthorities([]);
      service.getAuthenticationState().subscribe(account => (userIdentity = account));

      service.authenticate(null);

      expect(userIdentity).toBeNull();
      expect(service.isAuthenticated()).toBe(false);
    });

    it('authenticationState should emit the same account as was in input parameter', () => {
      const expectedResult = accountWithAuthorities([]);
      let userIdentity: Account | null = null;
      service.getAuthenticationState().subscribe(account => (userIdentity = account));

      service.authenticate(expectedResult);

      expect(userIdentity).toEqual(expectedResult);
    });
  });

  describe('identity', () => {
    it('should handle error when /account is on error', () => {
      service.identity(false).subscribe();

      httpMock.expectOne({ method: 'GET' }).flush('404 error', { status: 404, statusText: 'Not Found' });
    });

    it('should call /account only once if the last call has not returned', () => {
      service.identity().subscribe();

      service.identity().subscribe();

      httpMock.expectOne({ method: 'GET' });
    });

    it('should call /account only once if not logged out after first authentication and should call /account again if user has logged out', () => {
      service.identity().subscribe();
      httpMock.expectOne({ method: 'GET' }).flush({});

      service.identity().subscribe();

      httpMock.expectNone({ method: 'GET' });

      service.authenticate(null);

      service.identity().subscribe();

      httpMock.expectOne({ method: 'GET' });
    });
  });

  describe('hasAnyAuthority', () => {
    describe('hasAnyAuthority string parameter', () => {
      it('should return false if user is not logged', () => {
        const hasAuthority = service.hasAnyAuthority(Authority.USER);
        expect(hasAuthority).toBe(false);
      });

      it('should return false if a user is logged and has no authority', () => {
        service.authenticate(accountWithAuthorities([Authority.USER]));

        const hasAuthority = service.hasAnyAuthority(Authority.ADMIN);

        expect(hasAuthority).toBe(false);
      });

      it('should return true if a user is logged and has authority', () => {
        service.authenticate(accountWithAuthorities([Authority.USER]));

        const hasAuthority = service.hasAnyAuthority(Authority.USER);

        expect(hasAuthority).toBe(true);
      });
    });

    describe('hasAnyAuthority array parameter', () => {
      it('should return false if user is not logged', () => {
        const hasAuthority = service.hasAnyAuthority([Authority.USER]);
        expect(hasAuthority).toBeFalsy();
      });

      it('should return false if a user is logged and has no authority', () => {
        service.authenticate(accountWithAuthorities([Authority.USER]));

        const hasAuthority = service.hasAnyAuthority([Authority.ADMIN]);

        expect(hasAuthority).toBe(false);
      });

      it('should return true if a user is logged and has authority', () => {
        service.authenticate(accountWithAuthorities([Authority.USER]));

        const hasAuthority = service.hasAnyAuthority([Authority.USER, Authority.ADMIN]);

        expect(hasAuthority).toBe(true);
      });
    });
  });
});
