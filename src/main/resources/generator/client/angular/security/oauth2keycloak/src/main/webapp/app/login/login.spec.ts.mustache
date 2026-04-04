import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';

import { By } from '@angular/platform-browser';
import Keycloak from 'keycloak-js';
import { Oauth2AuthService } from '../auth/oauth2-auth.service';
import Login from './login';

describe('Login', () => {
  let fixture: ComponentFixture<Login>;

  let oauth2AuthService: Oauth2AuthService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        { provide: ComponentFixtureAutoDetect, useValue: true },
        { provide: Keycloak, useValue: {} },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Login);

    oauth2AuthService = TestBed.inject(Oauth2AuthService);
  });

  it('should log out on click on logout button', () => {
    vi.spyOn(oauth2AuthService, 'logout').mockImplementation(vi.fn());

    const logoutButton = fixture.debugElement.query(By.css('#btn-logout')).nativeElement as HTMLElement;
    logoutButton.click();

    expect(oauth2AuthService.logout).toHaveBeenCalledWith();
  });
});
