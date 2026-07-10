import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';
import { Router } from '@angular/router';
import { UserActivityService } from '../../services/UserActivityService';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  isLoading = true;
  errorMessage = '';
  currentUserId = 1; // در پروژه واقعی این آی‌دی را از سرویس Authentication می‌گیری

 // constructor(
   // private productService: ProductService,
    //private activityService: UserActivityService,
  constructor(private productService: ProductService , private activityService: UserActivityService) {
  }

  ngOnInit(): void {
    this.productService.getProducts().subscribe({
      next: (data: Product[]) => {
        this.products = data;
        this.isLoading = false;
      },
      error: (err: any) => {
        console.error(err);
        this.errorMessage = 'خطا در برقراری ارتباط با سرور و دریافت اطلاعات محصولات.';
        this.isLoading = false;
      }
    });
  }

  onProductClick(product: Product): void {
    // ۱. ارسال رویداد کلیک به بک‌اند (بدون بلاک کردن کاربر)
    this.activityService.logClick(this.currentUserId, product.id).subscribe({
      next: () => console.log('Click logged successfully'),
      error: (err) => console.error('Failed to log click', err)
    });

    // ۲. انتقال کاربر به صفحه جزئیات محصول

  }
}
