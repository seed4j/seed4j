import { TestBed } from '@angular/core/testing';
import { provideRouter, Router } from '@angular/router';

import { routes } from './app.route';

describe('AppRoutes', () => {
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideRouter(routes)],
    }).compileComponents();
    router = TestBed.inject(Router);
    router.initialNavigation();
  });

  it('should be defined', () => {
    expect(routes).toBeDefined();
  });

  // jhipster-needle-angular-menu
});
