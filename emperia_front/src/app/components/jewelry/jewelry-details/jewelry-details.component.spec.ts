import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JewelryDetailsComponent } from './jewelry-details.component';

describe('JewelryDetailsComponent', () => {
  let component: JewelryDetailsComponent;
  let fixture: ComponentFixture<JewelryDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JewelryDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JewelryDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
