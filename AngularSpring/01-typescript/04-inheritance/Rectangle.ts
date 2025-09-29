import {Comparable} from "./Comparable";
import {Shape} from "./Shape";

export class Rectangle extends Shape implements Comparable
{
  constructor(private _x1: number, private _y1: number, private _width: number, private _height: number)
  {
    super(_x1, _y1);
  }

  public getArea(): number
  {
    return this._width * this._height;
  }

  public toString(): string
  {
    return super.toString() + ` width=${this._width} height=${this._height}`;
  }

  compareTo(obj: Object): number
  {
    if (!(obj instanceof Rectangle))
      throw new Error("Object not instance of Rectangle");
    return this.getArea() - obj.getArea();
  }
}