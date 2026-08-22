import {Comparable} from "./Comparable";

export class Point implements Comparable
{
  constructor(private _x: number, private _y: number) { }

  compareTo(obj: Object): number
  {
    if (!(obj instanceof Point))
      throw new Error("Object not an instance of Point");
    let p: Point = obj;

    return (this._x == p._x) ? (this._y - p._y) : (this._x - p._x);
  }
}