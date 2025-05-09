import { Component } from '@angular/core';

@Component({
  selector: 'app-basic-entity',
  standalone: true,  // This makes it a standalone component
  templateUrl: './basic-entity.component.html',
  styleUrls: ['./basic-entity.component.css'],
})
export class BasicEntityComponent {
  id: number = 0;
  lastUpdate: number = 0;
  creationDate: number = 0;

  constructor() {}
}
