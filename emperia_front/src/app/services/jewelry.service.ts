import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Jewelry } from '../models/jewelry';

@Injectable({
  providedIn: 'root',
})
export class JewelryService {
  private baseUrl = environment.apiUrl + '/jewleries'; // Use environment variable

  constructor(private http: HttpClient) {}

  getJewleries(): Observable<Jewelry[]> {
    return this.http.get<Jewelry[]>(this.baseUrl);
  }

  getJewleryById(id: number): Observable<Jewelry> {
    return this.http.get<Jewelry>(`${this.baseUrl}/${id}`);
  }

  createJewlery(jewelry: Jewelry): Observable<Jewelry> {
    return this.http.post<Jewelry>(`${this.baseUrl}/create`, jewelry);
  }

  updateJewlery(id: number, jewelry: Jewelry): Observable<Jewelry> {
    return this.http.put<any>(`${this.baseUrl}/update/${id}`, jewelry);
  }

  deleteJewlery(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
