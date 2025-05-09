import { Component, inject } from '@angular/core';
import { CategoryComponent } from '../category/category.component';
import { BasicEntityComponent } from '../basic-entity/basic-entity.component';
import { JewelryService } from '../../services/jewelry.service';

@Component({
  selector: 'app-jewelry',
  standalone: true,
  templateUrl: './jewelry.component.html',
  styleUrls: ['./jewelry.component.css'],
  imports: [CategoryComponent, BasicEntityComponent], 
})
export class JewelryComponent extends BasicEntityComponent {
  private jewelryService = inject(JewelryService);

  name: string = 'Diamond Necklace';
  type: string = 'Necklace';
  price: number = 1000;
  description: string = 'A beautiful diamond necklace';
  materials: string[] = ['Gold', 'Diamond'];
  images: string[] = ['necklace1.jpg', 'necklace2.jpg'];

  category = {
    name: 'Necklaces',
  };

  constructor() {
    super();
    this.fetchJewleries();
  }

  fetchJewleries() {
    this.jewelryService.getJewleries().subscribe({
      next: (data) => console.log('Jewleries:', data),
      error: (err) => console.error('Error:', err),
    });
  }
}
