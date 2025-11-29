type Data = 
{
  name: string;
  count: number;
  
  // Index signature to optionally add any additional properties
  // This is a special syntax that allows addition of additional properties where key is string and value can be of any type
  [key: string]: any;
}

let d1:Data = { name:"Raghu", count:10 };
let d2:Data = { name:"Raghu", count:20, active:true, fruit:"Apple" };
let d3:Data = { name:"Raghu", count:20, free:false, veggie:"Carrot" };

console.log("Convert to JSON: d1=" + JSON.stringify(d1));
console.log("Ways to access type: d1.name=" + d1.name + " d1['name']=" + d1["name"]);
console.log("Additional properties using index signature: d2=" + JSON.stringify(d2));
console.log("Ways to access type: d2.active=" + d2.active + " d2['active']" + d2['active']);


