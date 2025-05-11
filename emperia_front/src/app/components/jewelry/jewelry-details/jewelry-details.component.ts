import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { catchError, of } from 'rxjs';
import { CommonModule, Location } from '@angular/common'; // To navigate back
import { Jewelry } from '../../../models/jewelry';
import { JewelryService } from '../../../services/jewelry.service';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ButtonModule } from 'primeng/button';
import { ImageModule } from 'primeng/image';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { TagModule } from 'primeng/tag';

@Component({
  selector: 'app-jewelry-details',
  templateUrl: './jewelry-details.component.html',
  styleUrls: ['./jewelry-details.component.css'],
  imports: [
    CommonModule,
    ProgressSpinnerModule,
    ButtonModule,
    ImageModule,
    RouterModule,
    CardModule,
    MessageModule,
    TagModule,
  ],
})
export class JewelryDetailsComponent implements OnInit {
  private jewelryService = inject(JewelryService);
  jewelry: Jewelry | null = null;
  isLoading: boolean = true;
  error: string | null = null;

  // Fallback Jewelry Data
  private fallbackJewelry: Jewelry = {
    id: 1,
    name: 'Fallback Jewelry',
    type: 'Ring',
    category: { id: 1, name: 'Rings' },
    price: 0,
    creationDate: Date.now(),
    description: 'This is fallback jewelry data.',
    materials: ['Gold'],
    images: ['https://example.com/placeholder.jpg'],
  };

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadJewelryDetails();
  }

  loadJewelryDetails(): void {
    const jewelryId = Number(this.route.snapshot.paramMap.get('id'));

    if (isNaN(jewelryId)) {
      this.error = 'Invalid jewelry ID.';
      this.isLoading = false;
      return;
    }

    this.jewelryService
      .getJewleryById(jewelryId)
      .pipe(
        catchError((err) => {
          this.error = 'Jewelry not found. Showing fallback data.';
          this.jewelry = this.fallbackJewelry; // Set fallback data
          this.isLoading = false;
          return of(null); // Return null or empty result
        })
      )
      .subscribe((jewelry) => {
        if (jewelry !== null) {
          this.jewelry = jewelry;
        }
        this.isLoading = false;
      });
  }

  goBack(): void {
    this.location.back();
  }
}
