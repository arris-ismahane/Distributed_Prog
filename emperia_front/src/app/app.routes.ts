// app.routes.ts
import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { JewelryFormComponent } from './components/jewelry/jewelry-form/jewelry-form.component'; // 👉 Import

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'create', component: JewelryFormComponent }, // 👉 Add route
];
