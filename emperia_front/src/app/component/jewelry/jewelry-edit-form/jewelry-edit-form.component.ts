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
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { JewelryInput } from '../../../models/jewelry-input';
import { Jewelry } from '../../../models/jewelry';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';

import { Location } from '@angular/common';
import { catchError, of } from 'rxjs';
@Component({
  selector: 'app-jewelry-edit-form',
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
    FileUploadModule,
  ],
  providers: [MessageService],
  templateUrl: './jewelry-edit-form.component.html',
  styleUrls: ['./jewelry-edit-form.component.css'],
})
export class JewelryEditFormComponent {
  jewelryForm: FormGroup;
  categories: Category[] = [];
  selectedFiles: File[] = [];
  isLoading = true;
  jewelryId: number;
  error: string = '';
  isEditMode = false;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private fb: FormBuilder,
    private jewelryService: JewelryService,
    private messageService: MessageService,
    private categoryService: CategoryService,
    private router: Router
  ) {
    this.jewelryForm = this.fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      categoryId: [null, Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
      description: [''],
      materials: this.fb.array([this.fb.control('')]),
      images: this.fb.array([this.fb.control('')]),
    });

    // Get the jewelry ID from the route params
    this.jewelryId = Number(this.route.snapshot.paramMap.get('id'));
    this.isEditMode = !isNaN(this.jewelryId);
  }

  ngOnInit(): void {
    // Load categories
    this.loadCategories();

    // If we're in edit mode, load the jewelry details
    if (this.isEditMode) {
      this.loadJewelryDetails();
    } else {
      this.isLoading = false;
    }
  }

  loadCategories(): void {
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

  loadJewelryDetails(): void {
    if (isNaN(this.jewelryId)) {
      this.error = 'Invalid jewelry ID.';
      this.isLoading = false;
      return;
    }

    this.jewelryService
      .getJewleryById(this.jewelryId)
      .pipe(
        catchError((err) => {
          this.error = 'Jewelry not found.';
          this.isLoading = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Failed to load jewelry details',
          });
          return of(null);
        })
      )
      .subscribe((jewelry) => {
        if (jewelry) {
          // Reset the form with the jewelry data
          this.jewelryForm.patchValue({
            name: jewelry.name,
            type: jewelry.type,
            categoryId: jewelry.category.id,
            price: jewelry.price,
            description: jewelry.description,
          });

          // Handle materials array
          if (jewelry.materials && jewelry.materials.length > 0) {
            this.materials.clear();
            jewelry.materials.forEach((material) => {
              this.materials.push(this.fb.control(material));
            });
          }

          // Note: We don't populate the images as they need to be uploaded again if changed
        }
        this.isLoading = false;
      });
  }

  get materials(): FormArray<FormControl> {
    return this.jewelryForm.get('materials') as FormArray<FormControl>;
  }

  addMaterial() {
    this.materials.push(this.fb.control(''));
  }

  removeMaterial(index: number) {
    this.materials.removeAt(index);
  }

  onFileSelected(event: any) {
    this.selectedFiles = [...event.files];
  }

  onSubmit() {
    if (this.jewelryForm.valid) {
      const jewelry: JewelryInput = this.jewelryForm.value;

      if (this.isEditMode) {
        // Update existing jewelry
        this.jewelryService.updateJewlery(this.jewelryId, jewelry).subscribe({
          next: (updatedJewelry) => {
            if (this.selectedFiles.length > 0) {
              this.uploadImages(this.jewelryId);
            } else {
              this.messageService.add({
                severity: 'success',
                summary: 'Success',
                detail: 'Jewelry updated successfully',
              });
              setTimeout(() => this.goBack(), 1000);
            }
          },
          error: (error) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Failed to update jewelry',
            });
            console.error('Update jewelry error:', error);
          },
        });
      } else {
        // Create new jewelry
        this.jewelryService.createJewlery(jewelry).subscribe({
          next: (createdJewelry) => {
            if (this.selectedFiles.length > 0) {
              this.uploadImages(createdJewelry.id);
            } else {
              this.messageService.add({
                severity: 'success',
                summary: 'Success',
                detail: 'Jewelry created successfully',
              });
              setTimeout(() => this.goBack(), 1000);
            }
          },
          error: (error) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Failed to create jewelry',
            });
            console.error('Create jewelry error:', error);
          },
        });
      }
    }
  }

  uploadImages(jewelryId: number) {
    this.jewelryService.uploadImages(jewelryId, this.selectedFiles).subscribe({
      next: (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: this.isEditMode
            ? 'Jewelry updated and images uploaded successfully'
            : 'Jewelry and images uploaded successfully',
        });
        setTimeout(() => this.goBack(), 1000);
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: this.isEditMode
            ? 'Jewelry updated but failed to upload images'
            : 'Jewelry created but failed to upload images',
        });
        console.error('Image upload error:', error);
      },
    });
  }

  goBack() {
    this.location.back();
  }
}
