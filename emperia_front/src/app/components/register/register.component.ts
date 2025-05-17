import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { RadioButtonModule } from 'primeng/radiobutton';
import { CalendarModule } from 'primeng/calendar';
import { EmperiaUserType } from '../../models/emperia-user-type.enum';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    CardModule,
    RouterModule,
    RadioButtonModule,
    CalendarModule, // Added for p-calendar
  ],
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  registerForm: FormGroup;
  error: string | null = null;
  userTypeEnum = EmperiaUserType;
  maxBirthDate = new Date();
  yearRange = `1900:${new Date().getFullYear()}`;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient // inject HttpClient to make HTTP calls
  ) {
    this.registerForm = this.fb.group(
      {
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        username: ['', Validators.required],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', Validators.required],
        birthDate: [null, Validators.required],
        userType: [EmperiaUserType.CUSTOMER, Validators.required],
      },
      {
        validators: this.passwordMatchValidator,
      }
    );
  }

  passwordMatchValidator(form: FormGroup) {
    return form.get('password')?.value === form.get('confirmPassword')?.value
      ? null
      : { mismatch: true };
  }

  onSubmit(): void {
    this.error = null;

    if (this.registerForm.invalid) {
      if (this.registerForm.errors?.['mismatch']) {
        this.error = 'Passwords do not match.';
      }
      return;
    }

    const formValue = this.registerForm.value;

    const userInput = {
      firstName: formValue.firstName!,
      lastName: formValue.lastName!,
      username: formValue.username!,
      password: formValue.password!,
      birthDate: formValue.birthDate
        ? new Date(formValue.birthDate).getTime()
        : 0,
      userType: formValue.userType!,
    };

    // Call the backend /auth/register endpoint
    this.http.post(`${environment.apiUrl}/auth/register`, userInput).subscribe({
      next: (res) => {
        // Registration successful, redirect to login page
        this.router.navigate(['/login']);
      },
      error: (err) => {
        // Handle backend validation or error message
        this.error = err.error || 'Registration failed.';
      },
    });
  }
}
