var d1 = { name: "Raghu", count: 10 };
var d2 = { name: "Raghu", count: 20, active: true, fruit: "Apple" };
var d3 = { name: "Raghu", count: 20, free: false, veggie: "Carrot" };
console.log("Convert to JSON: d1=" + JSON.stringify(d1));
console.log("Ways to access type: d1.name=" + d1.name + " d1['name']=" + d1["name"]);
console.log("Additional properties using index signature: d2=" + JSON.stringify(d2));
console.log("Ways to access type: d2.active=" + d2.active + " d2['active']" + d2['active']);
