import { Category } from "./category";

export interface Jewelry {
    id: number;
    name: string;
    type: string;
    category: Category;
    price: number;
    creationDate: number; // timestamp
    description: string;
    materials: string[];
    images: string[];
  }