import { Routes } from '@angular/router';
import { ProductListComponent } from './features/product-list/product-list.component';
import { ProductFormComponent } from './features/product-form/product-form.component';

import { CategoryListComponent } from './features/category-list/category-list.component';
import { CategoryFormComponent } from './features/category-form/category-form.component'

export const routes: Routes = [
  { path: '', component: ProductListComponent }, // صفحه اصلی سایت
  { path: 'products', component: ProductListComponent },
  { path: 'products/new', component: ProductFormComponent },
 { path: 'categories', component: CategoryListComponent },
{ path: 'categories/new', component: CategoryFormComponent },
  { path: '**', redirectTo: '' }
];
