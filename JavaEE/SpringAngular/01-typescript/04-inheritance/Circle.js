"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        if (typeof b !== "function" && b !== null)
            throw new TypeError("Class extends value " + String(b) + " is not a constructor or null");
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
exports.Circle = void 0;
var Shape_1 = require("./Shape");
var Circle = /** @class */ (function (_super) {
    __extends(Circle, _super);
    function Circle(_x1, _y1, _radius) {
        var _this = _super.call(this, _x1, _y1) || this;
        _this._x1 = _x1;
        _this._y1 = _y1;
        _this._radius = _radius;
        return _this;
    }
    Circle.prototype.getArea = function () {
        return Math.PI * Math.pow(this._radius, 2);
    };
    Circle.prototype.compareTo = function (obj) {
        if (!(obj instanceof Circle))
            throw new Error("Object not instance of Circle");
        return (this._radius - obj._radius);
    };
    Circle.prototype.toString = function () {
        return _super.prototype.toString.call(this) + " radius=".concat(this._radius);
    };
    return Circle;
}(Shape_1.Shape));
exports.Circle = Circle;
