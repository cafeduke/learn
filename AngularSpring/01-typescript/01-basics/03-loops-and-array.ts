/**
 *  Arrays and Loops
 *  ----------------------------------------------------------------------------------------------------
 */
let x = 0;
console.log
let reviews: number[] = [5, 4, 3, 5, 2, 3.5];
for (let i = 0; i < reviews.length; ++i)
  console.log(`${i}) ${reviews[i]}`);

let total = 0;
for (let currReview of reviews)
  total += currReview;
console.log("AverageReview:" + (total / reviews.length));

/**
  *  Aray operations
  *  ----------------------------------------------------------------------------------------------------
  */
let names = ["Raghu", "Rama", "Pavi"];

// Push to end of array
names.push("Hari");
console.log(`Names=${names}`);
