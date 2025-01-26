import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAnimalComponent } from './edit-animals.component';

describe('EditAnimalsComponent', () => {
  let component: EditAnimalComponent;
  let fixture: ComponentFixture<EditAnimalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditAnimalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditAnimalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
