import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAnimalsComponent } from './admin-animals.component';

describe('AdminAnimalsComponent', () => {
  let component: AdminAnimalsComponent;
  let fixture: ComponentFixture<AdminAnimalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAnimalsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminAnimalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
