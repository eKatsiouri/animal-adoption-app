import { Component, inject } from '@angular/core';
import { AnimalDetails } from '../../../../../shared/interfaces/animal';
import { AnimalService } from '../../../../../shared/services/animal.service';
import { AdminService } from '../../../../../shared/services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AnimalType } from '../../../../../shared/enums/animal-type';

@Component({
  selector: 'app-create-animals',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './create-animals.component.html',
  styleUrl: './create-animals.component.css'
})
export class CreateAnimalsComponent {
  animalDetails!: AnimalDetails;
  animalService =inject(AnimalService);
  adminService= inject(AdminService)
  route = inject(ActivatedRoute)
  animalForm!: FormGroup
  animalTypes!: AnimalType
  router = inject(Router)
   
  
    ngOnInit() {
  
      this.initForm();
    }
  
    private initForm() {
    this.animalForm = new FormGroup({
      name: new FormControl(this.animalDetails?.animal?.name || '', [Validators.required]),
      gender: new FormControl(this.animalDetails?.animal?.gender || '', [Validators.required]),
      animalType: new FormControl(this.animalDetails?.animal?.animalType || '', [Validators.required]),
      age: new FormControl(this.animalDetails?.animal?.age || 0),
      adopted: new FormControl(this.animalDetails?.animal?.adopted ?? false, [Validators.required]),
      isVaccinated: new FormControl(this.animalDetails?.moreInfo?.isVaccinated ?? false),
      isNeutered: new FormControl(this.animalDetails?.moreInfo?.isNeutered ?? false),
      color: new FormControl(this.animalDetails?.moreInfo?.color || '', [Validators.maxLength(30)]),
      breed: new FormControl(this.animalDetails?.moreInfo?.breed || '',[Validators.maxLength(30)]),
      hasSpecialDiet: new FormControl(this.animalDetails?.moreInfo?.hasSpecialDiet ?? false),
      isFriendlyWithKids: new FormControl(this.animalDetails?.moreInfo?.isFriendlyWithKids ?? false),
      isFriendlyWithOtherPets: new FormControl(this.animalDetails?.moreInfo?.isFriendlyWithOtherPets ?? false),
      isMicrochipped: new FormControl(this.animalDetails?.moreInfo?.isMicrochipped ?? false)})
      };
    
    onSubmit() {
      if (this.animalForm.invalid) {
        console.error('Form is invalid');
        console.log(this.animalForm.value);
        return;
      }

      console.log('Form Values:', this.animalForm.value);

      const formValues = this.animalForm.value;
    
      const addAnimalDetails: AnimalDetails = {
        
        animal: {
          id:  0,
          name: formValues.name,
          gender: formValues.gender,
          animalType: formValues.animalType,
          age: formValues.age,
          adopted: formValues.adopted,
          image: formValues.animal?.image
        },
        moreInfo: {
          id:  0,
          isVaccinated: formValues.isVaccinated,
          isNeutered: formValues.isNeutered,
          color: formValues.color,
          breed: formValues.breed,
          hasSpecialDiet: formValues.hasSpecialDiet,
          isFriendlyWithKids: formValues.isFriendlyWithKids,
          isFriendlyWithOtherPets: formValues.isFriendlyWithOtherPets,
          isMicrochipped: formValues.isMicrochipped
        }
      };
    
    
      this.adminService.createAnimal(addAnimalDetails).subscribe({
        next: (response) => {
          console.log('Animal created successfully!', response);
          this.router.navigate(['/success']);
        },
        error: (error) => {
          console.error('Error creating animal:', error);
        },
      });
    }}
