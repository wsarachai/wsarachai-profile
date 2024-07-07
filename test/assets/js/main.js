let s =
  '{"lec": [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0], "lab": [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}';

let atten = JSON.parse(s);

let lec = atten["lec"];
let lab = atten["lab"];

console.log(lec);
console.log(lab);
