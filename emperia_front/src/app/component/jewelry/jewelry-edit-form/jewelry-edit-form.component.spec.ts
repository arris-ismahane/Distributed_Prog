import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JewelryEditFormComponent } from './jewelry-edit-form.component';

describe('JewelryEditFormComponent', () => {
  let component: JewelryEditFormComponent;
  let fixture: ComponentFixture<JewelryEditFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JewelryEditFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JewelryEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
