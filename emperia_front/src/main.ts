// main.ts
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    provideRouter(routes)
  ]
}).catch(err => console.error(err));


/*
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { catchError, of } from 'rxjs';
import { environment } from './environments/environment';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app/app.component.html', // 👈 Changed to templateUrl
  imports: [],
})
class AppComponent {
  private http = inject(HttpClient);
  backendResponse: string = 'Loading...';

  constructor() {
    this.http.get<string>(`${environment.apiUrl}/api/`)
      .pipe(catchError(() => of('Error reaching backend')))
      .subscribe(response => {
        this.backendResponse = response;
      });
  }
}

bootstrapApplication(AppComponent, {
  providers: [provideHttpClient()]
}).catch(err => console.error(err));
*/