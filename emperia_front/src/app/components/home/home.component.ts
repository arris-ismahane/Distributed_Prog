// home.component.ts
import { Component, inject, OnInit } from '@angular/core';
import { catchError, of } from 'rxjs';
import { CardModule } from 'primeng/card';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { ImageModule } from 'primeng/image'; // <-- Add this import
import { ProgressSpinnerModule } from 'primeng/progressspinner'; // <-- For the spinner
import { JewelryService } from '../../services/jewelry.service';
import { Jewelry } from '../../models/jewelry';
import { RouterModule } from '@angular/router'; // <-- ADD THIS
import { ProfileBubbleComponent } from '../../components/profile-bubble/profile-bubble.component';
import { Category } from '../../models/category';
import { CategoryService } from '../../services/category.service';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { RatingModule } from 'primeng/rating';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [MessageService],
  imports: [
    CardModule,
    RouterModule,
    CommonModule,
    ButtonModule,
    ImageModule, // <-- Add this
    ProgressSpinnerModule, // <-- Add this if using p-progressSpinner
    ProfileBubbleComponent,
    DropdownModule,
    FormsModule,
    RatingModule,
    ToastModule,
  ],
})
export class HomeComponent implements OnInit {
  jewelries: Jewelry[] = [];
  isLoading = true;
  error: string | null = null;

  selectedCategoryId: number | null = null;

  categories: Category[] = [];
  sortOptions = [
    { label: 'Creation Date ↑', value: 'creationDate_asc' },
    { label: 'Creation Date ↓', value: 'creationDate_desc' },
    { label: 'Price ↑', value: 'price_asc' },
    { label: 'Price ↓', value: 'price_desc' },
  ];
  selectedSort = 'creationDate_asc';

  constructor(
    private jewelryService: JewelryService,
    private categoryService: CategoryService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.loadCategories();
    this.loadJewelries();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe((cats) => {
      this.categories = cats;
    });
  }

  loadJewelries(): void {
    this.isLoading = true;
    this.error = null;

    // Parse the sort option
    const [sortBy, order] = this.selectedSort.split('_');

    console.log('Fetching jewelries with:', {
      owned: true,
      categoryId: this.selectedCategoryId,
      sortBy,
      order,
    });

    // Pass undefined for categoryId when it's null
    // This is the key change
    this.jewelryService
      .getJewleries(
        false, // owned = true
        this.selectedCategoryId || undefined, // correctly handle null -> undefined
        sortBy, // sort field
        order // sort direction
      )
      .pipe(
        catchError((err) => {
          console.error('Error loading jewelries:', err);
          this.error = 'Failed to load jewelry items. Showing sample data.';
          this.isLoading = false;
          return of([]); // fallback empty array
        })
      )
      .subscribe((jewelries) => {
        this.jewelries = jewelries;
        this.isLoading = false;
      });
  }
  // Add debugging to see if this method is getting called
  onFiltersChanged(): void {
    console.log('Filters changed!', {
      category: this.selectedCategoryId,
      sort: this.selectedSort,
    });
    this.loadJewelries();
  }

  refresh(): void {
    this.loadJewelries();
  }

  getImageSrc(imageData: string): string {
    if (!imageData) {
      return 'assets/placeholder.jpg';
    }
    if (imageData.startsWith('data:')) {
      return imageData;
    }
    return 'data:image/jpeg;base64,' + imageData;
  }

  addToCart(_t33: Jewelry) {
    this.messageService.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Jewelry Added to cart successfully',
    });
  }
  getRandomRating(id: number): number {
    // Generate a deterministic pseudo-random number based on the item id
    // so rating doesn’t change every re-render.
    const seed = id * 9973; // any prime multiplier
    return Math.round(((seed % 5) + 1) * 2) / 2;
  }
}
