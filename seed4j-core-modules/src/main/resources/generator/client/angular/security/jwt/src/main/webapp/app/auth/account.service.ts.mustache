import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, of, ReplaySubject } from 'rxjs';
import { catchError, shareReplay, tap } from 'rxjs/operators';

import { Account } from './account.model';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private userIdentity: Account | null = null;
  private readonly authenticationState = new ReplaySubject<Account | null>(1);
  private accountCache$?: Observable<Account> | null;

  private readonly http = inject(HttpClient);

  authenticate(identity: Account | null): void {
    this.userIdentity = identity;
    this.authenticationState.next(this.userIdentity);
    if (!identity) {
      this.accountCache$ = null;
    }
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    if (!this.userIdentity) {
      return false;
    }
    if (!Array.isArray(authorities)) {
      authorities = [authorities];
    }
    return this.userIdentity.authorities.some((authority: string) => authorities.includes(authority));
  }

  identity(force = false): Observable<Account | null> {
    if (!this.accountCache$ || force) {
      this.accountCache$ = this.fetch().pipe(
        tap((account: Account) => {
          this.authenticate(account);
        }),
        shareReplay(),
      );
    }
    return this.accountCache$.pipe(catchError(() => of(null)));
  }

  isAuthenticated(): boolean {
    return this.userIdentity !== null;
  }

  getAuthenticationState(): Observable<Account | null> {
    return this.authenticationState.asObservable();
  }

  private fetch(): Observable<Account> {
    return this.http.get<Account>('api/account');
  }
}
