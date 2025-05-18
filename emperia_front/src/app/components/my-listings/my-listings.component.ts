// my-listings.component.ts
import { Component, OnInit } from '@angular/core';
import { catchError, of } from 'rxjs';
import { CardModule } from 'primeng/card';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { ImageModule } from 'primeng/image';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { JewelryService } from '../../services/jewelry.service';
import { Jewelry } from '../../models/jewelry';
import { RouterModule } from '@angular/router';
import { ProfileBubbleComponent } from '../../components/profile-bubble/profile-bubble.component';
import { Category } from '../../models/category';
import { CategoryService } from '../../services/category.service';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-my-listings',
  standalone: true,
  templateUrl: './my-listings.component.html',
  styleUrls: ['./my-listings.component.css'],
  providers: [MessageService, ConfirmationService],
  imports: [
    CardModule,
    RouterModule,
    CommonModule,
    ButtonModule,
    ImageModule,
    ProgressSpinnerModule,
    ProfileBubbleComponent,
    DropdownModule,
    FormsModule,
    ToastModule,
    ConfirmDialogModule
  ],
})
export class MyListingsComponent implements OnInit {
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
  selectedSort = 'creationDate_desc'; // Default to newest first

  constructor(
    private jewelryService: JewelryService,
    private categoryService: CategoryService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.loadCategories();
    this.loadMyJewelries();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe((cats) => {
      this.categories = cats;
    });
  }

  loadMyJewelries(): void {
    this.isLoading = true;
    this.error = null;

    // Parse the sort option
    const [sortBy, order] = this.selectedSort.split('_');

    console.log('Fetching my jewelries with:', {
      owned: true, // Explicitly fetch only owned items
      categoryId: this.selectedCategoryId,
      sortBy,
      order,
    });

    this.jewelryService
      .getJewleries(
        true, // Only show items owned by the current user
        this.selectedCategoryId || undefined,
        sortBy,
        order
      )
      .pipe(
        catchError((err) => {
          console.error('Error loading my jewelries:', err);
          this.error = 'Failed to load your jewelry items.';
          this.isLoading = false;
          return of([]); // fallback empty array
        })
      )
      .subscribe((jewelries) => {
        this.jewelries = jewelries;
        this.isLoading = false;
      });
  }

  onFiltersChanged(): void {
    console.log('Filters changed!', {
      category: this.selectedCategoryId,
      sort: this.selectedSort,
    });
    this.loadMyJewelries();
  }

  refresh(): void {
    this.loadMyJewelries();
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

  confirmDelete(item: Jewelry): void {
    this.confirmationService.confirm({
      message: `Are you sure you want to delete "${item.name}"?`,
      header: 'Delete Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.deleteJewelry(item);
      }
    });
  }

  deleteJewelry(item: Jewelry): void {
    this.jewelryService.deleteJewlery(item.id)
      .pipe(
        catchError((err) => {
          console.error('Error deleting jewelry:', err);
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Failed to delete jewelry item'
          });
          return of(null);
        })
      )
      .subscribe(() => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Jewelry item deleted successfully'
        });
        this.loadMyJewelries(); // Refresh the list
      });
  }
}