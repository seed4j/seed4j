import { NgOptimizedImage } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'seed-root',
  templateUrl: './app.html',
  imports: [RouterModule, MatMenuModule, MatToolbarModule, MatIconModule, MatButtonModule, NgOptimizedImage],
  styleUrl: './app.css',
})
export class App implements OnInit {
  appName = signal('');

  ngOnInit(): void {
    this.appName.set('seed4j');
  }
}
