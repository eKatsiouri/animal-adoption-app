import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalsMoreInfoComponent } from './animals-more-info.component';

describe('AnimalsMoreInfoComponent', () => {
  let component: AnimalsMoreInfoComponent;
  let fixture: ComponentFixture<AnimalsMoreInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnimalsMoreInfoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnimalsMoreInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
