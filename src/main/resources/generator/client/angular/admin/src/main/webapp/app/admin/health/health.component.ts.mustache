import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';

import { HealthService } from './health.service';
import { Health, HealthStatus } from './health.model';
import { HealthModalComponent } from './modal/health-modal.component';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'jhi-health',
  templateUrl: './health.component.html',
  imports: [CommonModule, MatDialogModule, MatIconModule, MatButtonModule, MatTableModule],
  standalone: true,
  styleUrl: './health.component.css',
})
export default class HealthComponent implements OnInit {
  displayedColumns: string[] = ['key', 'value', 'detail'];
  datasource: any = [];

  private dialog = inject(MatDialog);
  private healthService = inject(HealthService);

  ngOnInit(): void {
    this.refresh();
  }

  getBadgeClass(statusState: HealthStatus): string {
    if (statusState === 'UP') {
      return 'bg-success';
    }
    return 'bg-danger';
  }

  refresh(): void {
    this.healthService.checkHealth().subscribe({
      next: health => {
        this.datasource = Object.keys(health.components).map(key => {
          return {
            key: key,
            status: health.components[key].status,
            details: health.components[key].details,
          };
        });
      },
    });
  }

  showHealth(health: Health): void {
    this.dialog.open(HealthModalComponent, {
      data: health,
    });
  }
}
