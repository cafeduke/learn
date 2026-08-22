import {Comparable} from "./Comparable";
import {Shape} from "./Shape";

export class Circle extends Shape implements Comparable
{
  constructor(private _x1: number, private _y1: number, private _radius: number)
  {
    super(_x1, _y1);
  }

  public getArea(): number
  {
    return Math.PI * Math.pow(this._radius, 2);
  }

  compareTo(obj: Object): number
  {
    if (!(obj instanceof Circle))
      throw new Error("Object not instance of Circle");
    return (this._radius - obj._radius);
  }

  public toString(): string
  {
    return super.toString() + ` radius=${this._radius}`;
  }
}