import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { Router } from '@angular/router';
import { MenuModule } from 'primeng/menu'; // <-- Import this
import { AvatarModule } from 'primeng/avatar';
import { TooltipModule } from 'primeng/tooltip';
@Component({
  selector: 'app-profile-bubble',
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule,
    MenuModule,
    AvatarModule,
    TooltipModule,
  ],
  templateUrl: './profile-bubble.component.html',
  styleUrls: ['./profile-bubble.component.css'],
})
export class ProfileBubbleComponent {
  user = {
    firstName: '',
    lastName: '',
    username: '',
    birthdate: '',
  };

  showMenu = false;

  constructor(private router: Router) {
    // Load from localStorage
    const stored = localStorage.getItem('user');
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        this.user = {
          firstName: parsed.firstName || '',
          lastName: parsed.lastName || '',
          username: parsed.username || '',
          birthdate: parsed.birthdate || '',
        };
      } catch (e) {
        console.error('Invalid user object in localStorage', e);
      }
    }
  }

  toggleMenu() {
    this.showMenu = !this.showMenu;
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }
}
