import { Injectable,inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Animal, AnimalDetails, MoreAnimalInfo } from '../interfaces/animal';
import { environment } from '../../../environments/environment.development';

const API_URL = `${environment.apiURL}/animals`

@Injectable({
  providedIn: 'root'
})
export class AnimalService {

  http: HttpClient = inject(HttpClient);

  getAllAnimals() {
    return this.http.get<Animal[]>(`${API_URL}`)
  }

  getInfoByAnimalyId(id: number){
    return this.http.get<AnimalDetails>(`${API_URL}/${id}`)
  }

  getAnimalsForAdoption(){
    return this.http.get<Animal[]> (`${API_URL}/adoption`)
  }
}
