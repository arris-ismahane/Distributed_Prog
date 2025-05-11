import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Jewelry } from '../models/jewelry';
import { JewelryInput } from '../models/jewelry-input';

@Injectable({
  providedIn: 'root',
})
export class JewelryService {
  private baseUrl = environment.apiUrl + '/jewelries'; // Use environment variable

  constructor(private http: HttpClient) {}

  getJewleries(): Observable<Jewelry[]> {
    return this.http.get<Jewelry[]>(this.baseUrl);
  }

  getJewleryById(id: number): Observable<Jewelry> {
    return this.http.get<Jewelry>(`${this.baseUrl}/${id}`);
  }

  createJewlery(jewelry: JewelryInput): Observable<Jewelry> {
    return this.http.post<Jewelry>(`${this.baseUrl}/create`, jewelry);
  }

  updateJewlery(id: number, jewelry: JewelryInput): Observable<Jewelry> {
    return this.http.put<any>(`${this.baseUrl}/update/${id}`, jewelry);
  }

  deleteJewlery(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  uploadImages(jewelryId: number, images: File[]): Observable<any> {
    const url = `${this.baseUrl}/images/${jewelryId}`;

    // Create FormData and append all images with the same parameter name
    const formData = new FormData();
    for (const image of images) {
      formData.append('images', image);
    }

    // Send the PUT request with the FormData
    return this.http.put<any>(url, formData);
  }
}
