import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Login } from '../login/login.model';

type JwtToken = {
  id_token: string;
};

@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  private http = inject(HttpClient);

  login(credentials: Login): Observable<void> {
    return this.http.post<JwtToken>('api/authenticate', credentials).pipe(map(response => this.authenticateSuccess(response)));
  }

  logout(): Observable<void> {
    return new Observable(observer => {
      localStorage.removeItem('authenticationToken');
      observer.complete();
    });
  }

  private authenticateSuccess(response: JwtToken): void {
    const jwt = response.id_token;
    localStorage.setItem('authenticationToken', jwt);
  }
}
