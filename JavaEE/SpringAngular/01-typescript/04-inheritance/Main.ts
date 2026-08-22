import {Circle} from "./Circle";
import {Rectangle} from "./Rectangle";
import {Shape} from "./Shape";

let c1 = new Circle(5, 30, 50);
let c2 = new Circle(10, 10, 15);

let r1 = new Rectangle(30, 40, 10, 5);
let r2 = new Rectangle(10, 50, 30, 2);

// Shapes array
let shapes: Shape[] = [c1, c2, r1, r2];
console.log("--------------------------------------------------");
for (let currShape of shapes)
{
  console.log(`Shape=${currShape}`);
  console.log(`Area=${currShape.getArea()}`);
  console.log("--------------------------------------------------");
}
console.log(c1.compareTo(c2) >= 0 ? "c1 >= c2" : "c1 < c2");
console.log(r1.compareTo(r2) >= 0 ? "r1 >= r2" : "r1 < r2");
