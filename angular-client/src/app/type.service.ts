import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TypeService {
  private baseUrl = '/api/v1/types';


  constructor(private http: HttpClient) {
  }

  getType(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createType(type: object): Observable<any> {
    return this.http.post(`${this.baseUrl}`, type);
  }

  updateType(id: number, type: object): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, type);
  }

  deleteType(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, {responseType: 'text'});
  }

  getTypeList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }


}
