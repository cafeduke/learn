"use strict";
/**
 *  Exporting
 *  ---------
 *  A class needs to be exported in order to be acessible via 'import'
 */
Object.defineProperty(exports, "__esModule", { value: true });
exports.Customer = void 0;
var Customer = /** @class */ (function () {
    /**
     * Parameter properties
     * --------------------
     * Here, the properties _firstName and _lastName as parameters that generates properties with the same name.
     */
    function Customer(_firstName, _lastName) {
        this._firstName = _firstName;
        this._lastName = _lastName;
    }
    Object.defineProperty(Customer.prototype, "firstName", {
        /**
         * Getters and Setters accessors
         * -----------------------------
         * NOTE: syntax
         * public get <property-without-underscore> ():<type>
         * public set <property-without-underscore> (value: <type>)
         */
        get: function () {
            return this._firstName;
        },
        set: function (value) {
            this._firstName = value;
        },
        enumerable: false,
        configurable: true
    });
    Object.defineProperty(Customer.prototype, "lastName", {
        get: function () {
            return this._lastName;
        },
        set: function (value) {
            this._lastName = value;
        },
        enumerable: false,
        configurable: true
    });
    Customer.prototype.toString = function () {
        return "".concat(this._firstName, ".").concat(this._lastName);
    };
    return Customer;
}());
exports.Customer = Customer;
