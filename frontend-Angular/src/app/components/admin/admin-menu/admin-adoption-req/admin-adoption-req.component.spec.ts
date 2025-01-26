import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAdoptionReqComponent } from './admin-adoption-req.component';

describe('AdminAdoptionReqComponent', () => {
  let component: AdminAdoptionReqComponent;
  let fixture: ComponentFixture<AdminAdoptionReqComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAdoptionReqComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminAdoptionReqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
