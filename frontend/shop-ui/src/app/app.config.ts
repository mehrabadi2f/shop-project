import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),      // فعال‌سازی مکانیزم مسیریابی در پروژه
    provideHttpClient()         // فعال‌سازی ابزار ارتباط با APIهای بک‌اند (HttpClient)
  ]
};
