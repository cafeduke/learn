import { Component } from '@angular/core';
import { SalesPerson } from './sales-person';


@Component({
  selector: 'app-sales-person-list',
  imports: [],
  templateUrl: './sales-person-list.html',
  styleUrl: './sales-person-list.css'
})
export class SalesPersonList {
  listSalesPerson: SalesPerson[] =
  [
    new SalesPerson("Raghu", "Nandan", "raghubs81@gmail.com", 50000),
    new SalesPerson("Pavithra", "Rajagopal", "pavithra47@gmail.com", 40000),
    new SalesPerson("Shree", "Hari", "hari@gmail.com", 90000),
    new SalesPerson("Math", "Manja", "math.manja@gmail.com", 60000)
  ];

}
