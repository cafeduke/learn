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
exports.Rectangle = void 0;
var Shape_1 = require("./Shape");
var Rectangle = /** @class */ (function (_super) {
    __extends(Rectangle, _super);
    function Rectangle(_x1, _y1, _width, _height) {
        var _this = _super.call(this, _x1, _y1) || this;
        _this._x1 = _x1;
        _this._y1 = _y1;
        _this._width = _width;
        _this._height = _height;
        return _this;
    }
    Rectangle.prototype.getArea = function () {
        return this._width * this._height;
    };
    Rectangle.prototype.toString = function () {
        return _super.prototype.toString.call(this) + " width=".concat(this._width, " height=").concat(this._height);
    };
    Rectangle.prototype.compareTo = function (obj) {
        if (!(obj instanceof Rectangle))
            throw new Error("Object not instance of Rectangle");
        return this.getArea() - obj.getArea();
    };
    return Rectangle;
}(Shape_1.Shape));
exports.Rectangle = Rectangle;
