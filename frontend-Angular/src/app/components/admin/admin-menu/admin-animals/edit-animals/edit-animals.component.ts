import { Component, Inject, inject, NgModule } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnimalService } from '../../../../../shared/services/animal.service';
import { AdminService } from '../../../../../shared/services/admin.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, NgModel, ReactiveFormsModule, Validators } from '@angular/forms';
import { AnimalDetails } from '../../../../../shared/interfaces/animal';

@Component({
  selector: 'app-edit-animals',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './edit-animals.component.html',
  styleUrl: './edit-animals.component.css'
})


  export class EditAnimalComponent  {
    animalDetails!: AnimalDetails;
    animalService =inject(AnimalService);
    adminService= inject(AdminService)
    route = inject(ActivatedRoute)
    animalForm!: FormGroup
   
  
    ngOnInit(){
      const animalId = Number(this.route.snapshot.paramMap.get('id'));
    
      if (!animalId) {
        console.error('Invalid animal ID in route');
        return;
      }
    
      this.animalService.getInfoByAnimalyId(animalId).subscribe({
        next: (data) => {
          this.animalDetails = data;
          this.initForm();
        },
        error: (err) => {
          console.error('Error fetching animal details:', err);
        },
      });
    }
    
  
    private initForm() {
      if (!this.animalDetails || !this.animalDetails.animal) {
        console.error('Animal details are not available for form initialization');
        return;
      }
      
      this.animalForm = new FormGroup({
        name: new FormControl(this.animalDetails?.animal?.name || '', [Validators.required]),
        gender: new FormControl(this.animalDetails?.animal?.gender || '', [Validators.required]),
        animalType: new FormControl(this.animalDetails?.animal?.animalType || '', [Validators.required]),
        age: new FormControl(this.animalDetails?.animal?.age || 0, [Validators.required]),
        adopted: new FormControl(this.animalDetails?.animal?.adopted ?? false, [Validators.required]),
        isVaccinated: new FormControl(this.animalDetails?.moreInfo?.isVaccinated ?? false),
        isNeutered: new FormControl(this.animalDetails?.moreInfo?.isNeutered ?? false),
        color: new FormControl(this.animalDetails?.moreInfo?.color || ''),
        breed: new FormControl(this.animalDetails?.moreInfo?.breed || ''),
        hasSpecialDiet: new FormControl(this.animalDetails?.moreInfo?.hasSpecialDiet ?? false),
        isFriendlyWithKids: new FormControl(this.animalDetails?.moreInfo?.isFriendlyWithKids ?? false),
        isFriendlyWithOtherPets: new FormControl(this.animalDetails?.moreInfo?.isFriendlyWithOtherPets ?? false),
        isMicrochipped: new FormControl(this.animalDetails?.moreInfo?.isMicrochipped ?? false)})
    }
    
  
    onSubmit() {
      console.log(this.animalForm.value);
      if (this.animalForm.invalid) {
        console.error('Form is invalid');
        return;
      }
    
      const formValues = this.animalForm.value;
    
      const updatedAnimalDetails: AnimalDetails = {
        id: this.animalDetails.id,
        animal: {
          id: this.animalDetails.animal.id || 0,
          name: formValues.name,
          gender: formValues.gender,
          animalType: formValues.animalType,
          age: formValues.age,
          adopted: formValues.adopted,
          image: this.animalDetails.animal?.image
        },
        moreInfo: {
          id: this.animalDetails.moreInfo?.id || 0,
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
    
      this.adminService.updateAnimal(updatedAnimalDetails).subscribe({
        next: (response) => {
          console.log('Animal updated successfully!', response);
        },
        error: (error) => {
          console.error('Error updating animal:', error);
        },
      });
    }
  }