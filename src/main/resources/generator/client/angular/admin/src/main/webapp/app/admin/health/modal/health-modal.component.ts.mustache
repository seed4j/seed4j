import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { HealthDetails } from '../health.model';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'jhi-health-modal',
  templateUrl: './health-modal.component.html',
  imports: [CommonModule, MatDialogModule, MatIconModule, MatButtonModule, MatTableModule],
  standalone: true,
  styleUrl: './health-modal.component.css',
})
export class HealthModalComponent {
  GIGABYTE = 1073741824;
  MEGABYTE = 1048576;
  displayedColumns: string[] = ['key', 'value'];

  constructor(@Inject(MAT_DIALOG_DATA) public healthDetails: HealthDetails) {}

  readableValue(value: any): string {
    if (this.healthDetails.key === 'diskSpace') {
      // Should display storage space in an human readable unit
      const val = value / this.GIGABYTE;
      if (val > 1) {
        return `${val.toFixed(2)} GB`;
      }
      return `${(value / this.MEGABYTE).toFixed(2)} MB`;
    }

    if (typeof value === 'object') {
      return JSON.stringify(value);
    }
    return String(value);
  }
}
