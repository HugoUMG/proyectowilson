import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cuenta } from '../models/cuenta';

@Injectable({ providedIn: 'root' })
export class CuentasService {
  private base = '/api/cuentas';

  constructor(private http: HttpClient) {}

  list(): Observable<Cuenta[]> {
    return this.http.get<Cuenta[]>(this.base);
  }

  get(id: number): Observable<Cuenta> {
    return this.http.get<Cuenta>(`${this.base}/${id}`);
  }

  create(c: Partial<Cuenta>): Observable<Cuenta> {
    return this.http.post<Cuenta>(this.base, c);
  }

  update(id: number, c: Partial<Cuenta>): Observable<Cuenta> {
    return this.http.put<Cuenta>(`${this.base}/${id}`, c);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
