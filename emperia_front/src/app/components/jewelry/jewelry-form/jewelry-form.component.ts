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
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../models/category';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { Location } from '@angular/common'; // To navigate back

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
    FileUploadModule,
  ],
  providers: [MessageService],
  templateUrl: './jewelry-form.component.html',
  styleUrls: ['./jewelry-form.component.css'],
})
export class JewelryFormComponent {
  jewelryForm: FormGroup;
  categories: Category[] = [];
  selectedFiles: File[] = [];

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
    console.log('Selected files event:', event);
    this.selectedFiles = [...event.files];
    console.log('Selected files array:', this.selectedFiles);
  }

  onSubmit() {
    if (this.jewelryForm.valid) {
      const jewelry = this.jewelryForm.value;

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
            setTimeout(() => this.goHome(), 1000);
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

  uploadImages(jewelryId: number) {
    this.jewelryService.uploadImages(jewelryId, this.selectedFiles).subscribe({
      next: (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Jewelry and images uploaded successfully',
        });
        setTimeout(() => this.goHome(), 1000);
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Jewelry created but failed to upload images',
        });
        console.error('Image upload error:', error);
      },
    });
  }

  goHome() {
this.location.back();  }
}
