// home.component.ts
import { Component, inject, OnInit } from '@angular/core';
import { catchError, of } from 'rxjs';
import { CardModule } from 'primeng/card';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { JewelryService } from '../../services/jewelry.service';
import { Jewelry } from '../../models/jewelry';
import { Category } from '../../models/category';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [CardModule, CommonModule, ButtonModule],
})
export class HomeComponent implements OnInit {
  private jewelryService = inject(JewelryService);
  jewelries: Jewelry[] = [];
  isLoading: boolean = true;
  error: string | null = null;

  // Fallback jewelry data
  private fallbackJewelries: Jewelry[] = [
    {
      id: 1,
      name: 'Classic Gold Ring',
      type: 'Ring',
      category: { id: 1, name: 'Rings' },
      price: 299.99,
      creationDate: Date.now(),
      description: 'A beautiful classic gold ring with simple design',
      materials: ['Gold'],
      images: ['https://example.com/ring1.jpg']
    },
    {
      id: 2,
      name: 'Silver Pendant Necklace',
      type: 'Necklace',
      category: { id: 2, name: 'Necklaces' },
      price: 149.99,
      creationDate: Date.now(),
      description: 'Elegant silver pendant necklace with gemstone',
      materials: ['Silver', 'Gemstone'],
      images: ['https://example.com/necklace1.jpg']
    },
    {
      id: 3,
      name: 'Diamond Stud Earrings',
      type: 'Earrings',
      category: { id: 3, name: 'Earrings' },
      price: 499.99,
      creationDate: Date.now(),
      description: 'Luxurious diamond stud earrings in white gold',
      materials: ['White Gold', 'Diamond'],
      images: ['https://example.com/earrings1.jpg']
    }
  ];

  ngOnInit(): void {
    this.loadJewelries();
  }

  loadJewelries(): void {
    this.isLoading = true;
    this.error = null;
    
    this.jewelryService.getJewleries().pipe(
      catchError(err => {
        this.error = 'Failed to load jewelry items. Showing sample data.';
        this.isLoading = false;
        // Return fallback data instead of empty array
        return of(this.fallbackJewelries);
      })
    ).subscribe(jewelries => {
      this.jewelries = jewelries;
      this.isLoading = false;
    });
  }

  refresh(): void {
    this.loadJewelries();
  }
}