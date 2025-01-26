import { UserRole } from "../enums/user-role";
import { AdoptionRequest, AdoptionRequestDTO } from "./adoption-request";

export interface User {
  id: number;  
  firstname: string;
  lastname: string;
  phoneNumber: string;
  address: string;
  ssn: string;
  username: string;
  password: string;
  email: string;
  role: string;
  adoptionRequests?: AdoptionRequestDTO[]; 
  }
