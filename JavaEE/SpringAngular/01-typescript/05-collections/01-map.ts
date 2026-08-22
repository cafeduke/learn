let myMap: Map<string, number> = new Map();

myMap.set('apple', 10);
myMap.set('banana', 20);
 
let count1 = (myMap.get('banana') || 0) + 1;
let count2 = (myMap.get('grape') || 0) + 1;
console.log(`Banana=${count1} Grape=${count2}`);

