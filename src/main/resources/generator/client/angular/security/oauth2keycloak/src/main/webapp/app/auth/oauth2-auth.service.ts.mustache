import { inject, Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { from, Observable, tap } from 'rxjs';

const MIN_TOKEN_VALIDITY_SECONDS = 70;

@Injectable({ providedIn: 'root' })
export class Oauth2AuthService {
  private readonly keycloak: Keycloak = inject(Keycloak);

  get token(): string | undefined {
    return this.keycloak.token;
  }

  get isAuthenticated(): boolean | undefined {
    return this.keycloak.authenticated;
  }

  initAuthentication(): Observable<boolean> {
    return from(this.keycloak.init({ onLoad: 'login-required', checkLoginIframe: false })).pipe(
      tap(authenticated => {
        if (authenticated) {
          console.debug('Authenticated');
        } else {
          globalThis.location.reload();
        }
      }),
      tap(() => {
        this.initUpdateTokenRefresh();
      }),
    );
  }

  logout(): void {
    this.keycloak.logout();
  }

  private initUpdateTokenRefresh(): void {
    this.keycloak
      .updateToken(MIN_TOKEN_VALIDITY_SECONDS)
      .then(refreshed => {
        if (refreshed) {
          console.debug('Token refreshed');
        } else {
          const exp = this.keycloak.tokenParsed!.exp!;
          const timeSkew = this.keycloak.timeSkew!;
          console.debug(`Token not refreshed, valid for ${Math.round(exp + timeSkew - Date.now() / 1000)} seconds`);
        }
      })
      .catch((e: unknown) => console.error('Failed to refresh token', e));
  }
}
