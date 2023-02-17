import { HttpRequest, HttpHandlerFn } from '@angular/common/http';

export function AuthInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const token: string | null = localStorage.getItem('authenticationToken');
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
  return next(req);
}
