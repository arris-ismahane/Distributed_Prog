import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormArray,
  Validators,
  ReactiveFormsModule,
  FormControl,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { FormsModule } from '@angular/forms';
import { JewelryService } from '../../../services/jewelry.service';
import { RouterModule } from '@angular/router'; // <-- ADD THIS
import { CategoryService } from '../../../services/category.service'; // Adjust path if needed
import { Category } from '../../../models/category';
import { DropdownModule } from 'primeng/dropdown'; // ✅ Import this

@Component({
  selector: 'app-jewelry-form',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule,
    InputTextModule,
    InputNumberModule,
    ButtonModule,
    CardModule,
    MessageModule,
    ToastModule,
    DropdownModule,
  ],
  providers: [MessageService],
  templateUrl: './jewelry-form.component.html',
  styleUrls: ['./jewelry-form.component.css'],
})
export class JewelryFormComponent {
  jewelryForm: FormGroup;
  categories: Category[] = [];

  constructor(
    private fb: FormBuilder,
    private jewelryService: JewelryService,
    private messageService: MessageService,
    private categoryService: CategoryService // ✅ Inject here
  ) {
    this.jewelryForm = this.fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      categoryId: [null, Validators.required], // ✅ Use categoryId instead of nested group
      price: [0, [Validators.required, Validators.min(0)]],
      description: [''],
      materials: this.fb.array([this.fb.control('')]),
      images: this.fb.array([this.fb.control('')]),
    });
  }
  ngOnInit(): void {
    this.categoryService.getCategories().subscribe({
      next: (data) => (this.categories = data),
      error: () => {
        this.messageService.add({
          severity: 'warn',
          summary: 'Fallback',
          detail: 'Using fake categories. Backend not reachable.',
        });
        this.categories = [
          { id: 1, name: 'Necklaces' },
          { id: 2, name: 'Rings' },
          { id: 3, name: 'Bracelets' },
          { id: 4, name: 'Earrings' },
          { id: 5, name: 'Anklets' },
        ];
      },
    });
  }
  // In your component class, update the getters to return FormArray with proper typing
  get materials(): FormArray {
    return this.jewelryForm.get('materials') as FormArray<
      FormControl<string | null>
    >;
  }

  get images(): FormArray {
    return this.jewelryForm.get('images') as FormArray<
      FormControl<string | null>
    >;
  }

  addMaterial() {
    this.materials.push(this.fb.control(''));
  }

  removeMaterial(index: number) {
    this.materials.removeAt(index);
  }

  addImage() {
    this.images.push(this.fb.control(''));
  }

  removeImage(index: number) {
    this.images.removeAt(index);
  }

  onSubmit() {
    if (this.jewelryForm.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Invalid Form',
        detail: 'Please fill in all required fields.',
      });
      return;
    }

    const selectedCategory = this.categories.find(
      (cat) => cat.id === this.jewelryForm.value.categoryId
    );

    const newJewelry = {
      ...this.jewelryForm.value,
      id: 0,
      creationDate: Date.now(),
      category: selectedCategory!, // Assumes always found
    };

    this.jewelryService.createJewlery(newJewelry).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Jewelry added successfully.',
        });
        this.jewelryForm.reset();
        this.materials.clear();
        this.images.clear();
        this.materials.push(this.fb.control(''));
        this.images.push(this.fb.control(''));
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to add jewelry.',
        });
      },
    });
  }
}
