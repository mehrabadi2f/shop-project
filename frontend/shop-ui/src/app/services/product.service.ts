import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

import { CreateProductRequest } from '../models/create-product-request.model';

@Injectable({
  providedIn: 'root' // به صورت سرتاسری در پروژه قابل استفاده است
})
export class ProductService {
  // آدرس کنترلر بک‌اند
  private apiUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  // دریافت لیست محصولات به صورت Observable
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }
  // ایجاد محصول جدید
  createProduct(productData: CreateProductRequest): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, productData);
  }
}
