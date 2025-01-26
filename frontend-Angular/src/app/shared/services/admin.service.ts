import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Animal, AnimalDetails } from '../interfaces/animal';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AdoptionRequestDTO } from '../interfaces/adoption-request';
const API_URL = `${environment.apiURL}/login/admin`
@Injectable({
  providedIn: 'root'
})
export class AdminService {
http: HttpClient = inject(HttpClient)
    router = inject(Router);

  
  getAnimals() {
    return this.http.get<Animal[]>(`${API_URL}/animals`);
  }

 
  createAnimal(animal: AnimalDetails) {
    return this.http.post<AnimalDetails>(`${API_URL}/animals`, animal);
  }

 
  deleteAnimal(id: number) {
    return this.http.delete<void>(`${API_URL}/animals/${id}`);
  }

  updateAnimal(animalDetails: AnimalDetails)  {
    return this.http.put<AnimalDetails>(`${API_URL}/animals/${animalDetails.animal.id}`,animalDetails);
  }

  getAdoptionRequests() {
    return this.http.get<AdoptionRequestDTO[]>(`${API_URL}/adoptions`);
  }

  updateAdoptionRequestStatus(requestId: number, adoption : AdoptionRequestDTO) {
    return this.http.patch<AdoptionRequestDTO>(`${API_URL}/adoptions/${requestId}`,  adoption );
  }

  getAdoptionRequestById(id: number) {
    return this.http.get<AdoptionRequestDTO>(`${API_URL}/adoptions/${id}`);
  }
}
