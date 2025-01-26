import { AdoptionStatus } from "../enums/adoption-status";
import { User } from "./user";

export interface AdoptionRequest {
    userId: number; 
    animalId: number;
  }
 
  export interface AdoptionRequestDTO {
    id: number;
    animalId: number;
    username : string;
    userId: number;
    requestDate: string;
    adoptionRequestStatus: AdoptionStatus;
    animalName: string;
  }
