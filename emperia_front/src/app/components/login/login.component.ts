// login.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';
import { CardModule } from 'primeng/card';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    ReactiveFormsModule,
    CardModule,
    RouterModule,
  ],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginForm: FormGroup;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }
  goToRegister() {
    this.router.navigate(['/register']); // You’ll create this route
  }
  onSubmit() {
    if (this.loginForm.valid) {
      this.error = null;
      this.http
        .post<any>(`${environment.apiUrl}/auth/login`, this.loginForm.value)
        .subscribe({
          next: (res) => {
            localStorage.setItem('token', res.token); // Save the token

            // Convert birthdate number to Date string
            const birthdate = new Date(res.birthdate);

            // Save user info as a JSON string
            localStorage.setItem(
              'user',
              JSON.stringify({
                firstName: res.firstName,
                lastName: res.lastName,
                username: res.username,
                birthdate: birthdate.toISOString(),
              })
            );

            // Optional: save username and role separately
            localStorage.setItem('role', res.role);

            // Go to home page after successful login
            this.router.navigate(['/home']);
          },
          error: () => {
            this.error = 'Invalid credentials';
          },
        });
    }
  }
}
