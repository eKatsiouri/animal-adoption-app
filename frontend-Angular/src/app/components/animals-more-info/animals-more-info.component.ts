import { Component, inject } from '@angular/core';
import { AnimalService } from '../../shared/services/animal.service';
import { Animal, AnimalDetails } from '../../shared/interfaces/animal';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AdoptionRequestComponent } from '../menu/adoption-requests/adoption-requests.component';
import { UserService } from '../../shared/services/user.service';
import { jwtDecode } from 'jwt-decode';
import { AdoptionRequest } from '../../shared/interfaces/adoption-request';
import { User } from '../../shared/interfaces/user';



@Component({
  selector: 'app-animals-more-info',
  standalone: true,
  imports: [CommonModule, AdoptionRequestComponent],
  templateUrl: './animals-more-info.component.html',
  styleUrl: './animals-more-info.component.css'
})
export class AnimalsMoreInfoComponent {
  userService = inject(UserService)
  animalService = inject(AnimalService)
  route = inject(ActivatedRoute)
  animalDetails? : AnimalDetails 
  isModalOpen = false
  animal?: Animal 
  router = inject(Router)
  currentUser!: User;


    

  

  ngOnInit (){
    
    const id =Number (this.route.snapshot.paramMap.get('id')!)
    this.animalService.getInfoByAnimalyId(id).subscribe({
      next: (data) => {
        this.animalDetails=data;
      },
      error: (err) => {
        console.error('Error fetching animal details:', err);
      }
    })

    const currentUser = this.userService.getCurrentUser();
      if (currentUser && currentUser.username !== undefined) { 
        this.userService.getUserByUsername(currentUser.username).subscribe({
          next: (user) => {
            this.currentUser = user; 
          },
          error: (err) => {
            console.error('Error fetching user details:', err);
          },
        });
      } else {
        console.error('Current user or user ID is undefined');
      }

    }
  openAdoptionRequestForm() {
    this.isModalOpen = true;
    
  
  }
  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  openLogin() {
    this.router.navigate(['/auth/login']);
    this.closeModal();
  }

  openSignup() {
    this.router.navigate(['/register']);
    this.closeModal();
  }
  
 
  
 
}
