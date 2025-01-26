import { Component, inject } from '@angular/core';
import { Animal, AnimalDetails, MoreAnimalInfo } from '../../../shared/interfaces/animal';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AnimalService } from '../../../shared/services/animal.service';
import id from '@angular/common/locales/id';

@Component({
  selector: 'app-animals-for-adoption',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './animals-for-adoption.component.html',
  styleUrl: './animals-for-adoption.component.css'
})
export class AnimalsForAdoptionComponent {
  animalService = inject(AnimalService)
  router = inject(Router)
  animal : Animal[] = [];
  animalMoreInfo: MoreAnimalInfo [] = [];


  ngOnInit() {
    this.animalService.getAnimalsForAdoption().subscribe({
      next: (data : Animal[] ) => {
          this.animal = data;
      },
      error: (err) => {
        console.error('Error fetching animals for adoption:', err);
      }
    });
  }
  MoreInfo(animalId : number){
    this.router.navigate(['/all-animals/',animalId])
  }
  

}


