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
  ],
  providers: [MessageService],
  templateUrl: './jewelry-form.component.html',
  styleUrls: ['./jewelry-form.component.css'],
})
export class JewelryFormComponent {
  jewelryForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private jewelryService: JewelryService,
    private messageService: MessageService
  ) {
    this.jewelryForm = this.fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      category: this.fb.group({
        id: [1],
        name: ['', Validators.required],
      }),
      price: [0, [Validators.required, Validators.min(0)]],
      description: [''],
      materials: this.fb.array([this.fb.control('')]),
      images: this.fb.array([this.fb.control('')]),
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

    const newJewelry = {
      ...this.jewelryForm.value,
      id: 0,
      creationDate: Date.now(),
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
