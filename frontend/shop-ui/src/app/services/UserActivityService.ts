// user-activity.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserActivityService {
  private apiUrl = 'http://localhost:8080/api/events';

  constructor(private http: HttpClient) {}

  // ارسال رویداد کلیک روی محصول به بک‌اند
  logClick(userId: number, productId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/click`, null, {
      params: { userId: userId.toString(), productId: productId.toString() }
    });
  }
}
