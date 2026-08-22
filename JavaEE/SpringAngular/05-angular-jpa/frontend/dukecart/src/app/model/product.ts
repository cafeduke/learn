export interface Product
{
  id: number;
  name: string;
  description: string;
  unitPrice: number;
  unitsInStock: number;
  sku: string;
  active: boolean;
  imageUrl: string;
  dateCreated: Date;
  dateUpdated: Date;
}
