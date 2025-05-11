import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JewelryFormComponent } from './jewelry-form.component';

describe('JewelryFormComponent', () => {
  let component: JewelryFormComponent;
  let fixture: ComponentFixture<JewelryFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JewelryFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JewelryFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
