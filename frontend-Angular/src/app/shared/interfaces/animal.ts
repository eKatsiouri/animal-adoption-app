import { AdoptionStatus } from "../enums/adoption-status";
import { AnimalType } from "../enums/animal-type";
import { GenderType } from "../enums/gender-type";
import { User } from "./user";

export interface Animal {

    id: number;
    name: string;
    gender: GenderType;  
    animalType: AnimalType;  
    age: number;
    adopted: boolean;
    image: string;
  }




  export interface MoreAnimalInfo {
    id?: number;
    isVaccinated: boolean;
    isNeutered: boolean;
    color: string;
    isMicrochipped: boolean;
    hasSpecialDiet: boolean;
    isFriendlyWithOtherPets: boolean;
    isFriendlyWithKids: boolean;
    breed: string;
  }

  export interface AnimalDetails {
    id?: number;
    animal: Animal;
    moreInfo?: MoreAnimalInfo;
  }
