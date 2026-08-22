"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Circle_1 = require("./Circle");
var Rectangle_1 = require("./Rectangle");
var c1 = new Circle_1.Circle(5, 30, 50);
var c2 = new Circle_1.Circle(10, 10, 15);
var r1 = new Rectangle_1.Rectangle(30, 40, 10, 5);
var r2 = new Rectangle_1.Rectangle(10, 50, 30, 2);
// Shapes array
var shapes = [c1, c2, r1, r2];
console.log("--------------------------------------------------");
for (var _i = 0, shapes_1 = shapes; _i < shapes_1.length; _i++) {
    var currShape = shapes_1[_i];
    console.log("Shape=".concat(currShape));
    console.log("Area=".concat(currShape.getArea()));
    console.log("--------------------------------------------------");
}
console.log(c1.compareTo(c2) >= 0 ? "c1 >= c2" : "c1 < c2");
console.log(r1.compareTo(r2) >= 0 ? "r1 >= r2" : "r1 < r2");
