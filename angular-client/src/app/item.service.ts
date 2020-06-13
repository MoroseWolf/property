import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private baseUrl = '/api/v1/items';

  constructor(private http: HttpClient) {
  }

  getItem(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createItem(item: object): Observable<any> {
    return this.http.post(`${this.baseUrl}`, item);
  }

  updateItem(id: number, item: object): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, item);
  }

  deleteItem(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, {responseType: 'text'});
  }

  getItemsList(page: number, size: number, sortBy: string, filters: string[][]): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      Accept: 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
      'Accept-Language': 'ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7',
    });
    const body = new URLSearchParams();
    body.set('page', page.toString());
    body.set('size', size.toString());
    body.set('sort', sortBy);
    body.set('filter', this.JSON.stringify(filters).toString());
    console.log(`${this.baseUrl}` + '?' + body.toString());
    return this.http.get(`${this.baseUrl}` + '?' + body.toString(), { headers, withCredentials: true });
  }
}
