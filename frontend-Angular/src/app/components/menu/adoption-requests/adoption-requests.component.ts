import { Component, inject } from '@angular/core';
import { AnimalService } from '../../../shared/services/animal.service';
import { UserService } from '../../../shared/services/user.service';
import { Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdoptionStatus } from '../../../shared/enums/adoption-status';
import { AdoptionRequest } from '../../../shared/interfaces/adoption-request';
import { User } from '../../../shared/interfaces/user';
import { AnimalDetails } from '../../../shared/interfaces/animal';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-adoption-requests',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './adoption-requests.component.html',
  styleUrl: './adoption-requests.component.css'
})



export class AdoptionRequestComponent {
  @Input() animalId!: number;  
    
  userId?: number;
  animalService = inject(AnimalService);
  userService = inject(UserService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  currentUser: User | null = null;

  isModalOpen = false
  

 
  
    isConfirmed: boolean = false;


    onConfirmAdoption() {
      this.isConfirmed = true;
    }
  
    onCancelAdoption() {
      this.isConfirmed = false;
      this.router.navigate([`/all-animals`]);
    }

    openModal() {
      this.isModalOpen = true;
    }
  
    closeModal() {
      this.isModalOpen = false;
    }
    ngOnInit() {
      const currentUsername = this.userService.getCurrentUser()?.username;
    
    if (!currentUsername) {
      console.error('No user is logged in');
      this.router.navigate(['/auth/login']);
      return;
    }

   
    this.userService.getUserByUsername(currentUsername).subscribe({
      next: (userData) => {
        this.currentUser = userData;
        this.userId = this.currentUser.id; 
        console.log('User data:', this.currentUser);
      },
      error: (err) => {
        console.error('Error fetching user:', err);
      }
    });
  }
    
    onSubmitAdoptionRequest() {
      
      if (!this.userId || !this.animalId) {
        console.error('User ID or Animal ID is missing.');
        return;
      }
    
  
      const adoptionRequest: AdoptionRequest = {
        userId: this.userId,
        animalId: this.animalId,
      };
    
      console.log('Adoption Request:', adoptionRequest);
    
    
      this.userService.submitAdoptionRequest(adoptionRequest).subscribe({
        next: (response) => {
          console.log('Adoption request submitted successfully:', response);
          this.closeModal();
          this.router.navigate(['/success']); 
        },
        error: (error) => {
          console.error('Error submitting adoption request:', error);
          if (error.status === 409) {
            return ('There is already a pending adoption request for this animal.');
          } else {
            return ('Something went wrong. Please try again later.');
          }
        }
      });
    }
  
  }
     
      

 






