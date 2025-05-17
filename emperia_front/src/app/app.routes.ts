import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { JewelryDetailsComponent } from './components/jewelry/jewelry-details/jewelry-details.component';
import { JewelryFormComponent } from './components/jewelry/jewelry-form/jewelry-form.component';
import { AuthGuard } from './services/auth.guard.spec';

export const routes: Routes = [
  // Login & Register (public)
  {
    path: 'login',
    loadComponent: () =>
      import('./components/login/login.component').then(
        (m) => m.LoginComponent
      ),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./components/register/register.component').then(
        (m) => m.RegisterComponent
      ),
  },

  // Default route
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // Protected routes
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'create',
    component: JewelryFormComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'jewelry/:id',
    component: JewelryDetailsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: '**',
    loadComponent: () =>
      import('./components/not-found/not-found.component').then(
        (m) => m.NotFoundComponent
      ),
  },
];
