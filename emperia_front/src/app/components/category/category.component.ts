import { Component, Input } from '@angular/core';
import { BasicEntityComponent } from '../basic-entity/basic-entity.component';

@Component({
  selector: 'app-category',
  standalone: true,
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
  imports: [BasicEntityComponent],
})
export class CategoryComponent extends BasicEntityComponent {
  @Input() name: string = '';

  constructor() {
    super();
  }
}
