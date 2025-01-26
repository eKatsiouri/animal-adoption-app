import { Animal } from "./animal";

export interface Shelter {
    id: number;
    name: string;
    address: string;
    phoneNumber: string;
    animals: Animal[];  
  }