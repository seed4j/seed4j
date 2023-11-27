import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Oauth2AuthService } from '../auth/oauth2-auth.service';

import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  imports: [CommonModule, MatButtonModule],
  standalone: true,
})
export default class LoginComponent {
  constructor(private oauth2AuthService: Oauth2AuthService) {}

  logout(): void {
    this.oauth2AuthService.logout();
  }
}
