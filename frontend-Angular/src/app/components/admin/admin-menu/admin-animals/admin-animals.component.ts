import { Component, inject } from '@angular/core';
import { AnimalService } from '../../../../shared/services/animal.service';
import { Animal, AnimalDetails } from '../../../../shared/interfaces/animal';
import { AdminService } from '../../../../shared/services/admin.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-admin-animals',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './admin-animals.component.html',
  styleUrl: './admin-animals.component.css'
})
export class AdminAnimalsComponent {
  animals: Animal[] = [];
 animalService = inject(AnimalService)
 adminService = inject(AdminService)
 animalDetail?: AnimalDetails;
 animalDetails :AnimalDetails[] = [];

  ngOnInit(): void {
    this.getAnimals();
  }


  getAnimals(){
    
      this.animalService.getAllAnimals().subscribe({
        next: (data) => {
          this.animals = data; 
          console.log('Animals loaded:', this.animals); 
        },
        error: (err) => {
          console.error('Error fetching animals:', err);
        }})
    
  }


  editAnimal(animal : AnimalDetails ) {
   this.adminService.updateAnimal(animal).subscribe({
    next: (data) => {
      this.animalDetail = data; 
      console.log('Animal properties changed:', this.animalDetail); 
    },
    error: (err) => {
      console.error('Error editing animal:', err);
    }})

  
  }


  deleteAnimal(id: number): void {
    this.adminService.deleteAnimal(id).subscribe(() => {
      this.getAnimals(); 
    });
  }
}
