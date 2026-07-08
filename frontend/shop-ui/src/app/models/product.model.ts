export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  reservedStock: number;
  categoryId: number;
  categoryName: string;
}
