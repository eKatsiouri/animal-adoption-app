import { Component, inject } from '@angular/core';
import { AnimalService } from '../../../shared/services/animal.service';
import { Animal } from '../../../shared/interfaces/animal';
import { CommonModule } from '@angular/common';
import { Router,ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-all-animals',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './all-animals.component.html',
  styleUrl: './all-animals.component.css'
})
export class AllAnimalsComponent {
  animalService = inject(AnimalService)
  router = inject(Router)
  
  animals: Animal[] = [];


  ngOnInit(){
    this.animalService.getAllAnimals().subscribe({
      next:(data : Animal[]) => {this.animals = data},
      error: (err) => {
        console.log("Error")
      }
    })
  }

  MoreInfo(animalId : number){
    this.router.navigate(['/all-animals/',animalId])
  }


  

}
