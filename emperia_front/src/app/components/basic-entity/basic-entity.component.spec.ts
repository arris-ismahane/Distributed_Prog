import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BasicEntityComponent } from './basic-entity.component';

describe('BasicEntityComponent', () => {
  let component: BasicEntityComponent;
  let fixture: ComponentFixture<BasicEntityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BasicEntityComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BasicEntityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
