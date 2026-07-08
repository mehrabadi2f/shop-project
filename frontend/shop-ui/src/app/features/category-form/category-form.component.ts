import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { CategoryService } from '../../services/category.service'; // اصلاح مسیر ایمپورت
//////////
@Component({
  selector: 'app-category-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent implements OnInit {

  categoryForm!: FormGroup;
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  // Getter برای دسترسی به کنترل در تمپلت HTML بدون نیاز به get() مجدد
  get nameControl(): AbstractControl | null {
    return this.categoryForm.get('name');
  }

  submit(): void {
    if (this.categoryForm.invalid) {
      this.categoryForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;

    const requestData = {
      name: this.categoryForm.value.name
    };

    this.categoryService.createCategory(requestData).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigate(['/categories']);
      },
      error: (err) => {
        console.error('خطا در ثبت دسته‌بندی', err);
        this.isSubmitting = false;
      }
    });
  }
}
