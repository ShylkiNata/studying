"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
require("rxjs/add/operator/map");
require("rxjs/add/operator/catch");
require("rxjs/add/observable/throw");
var ValidationService = (function () {
    function ValidationService() {
    }
    ValidationService.prototype.checkEmail = function (email) {
        var pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (pattern.test(email))
            return "";
        else
            return "Invalid user email";
    };
    ValidationService.prototype.checkPassword = function (password) {
        if (password.length == 0)
            return "Enter your password";
        if (password.length < 5)
            return "Password must be at least 5 characters long";
        return "";
    };
    ValidationService.prototype.checkRePassword = function (password, repass) {
        if (repass.length == 0)
            return "Fill the re-entered password field";
        if (repass != password || repass.length < 5)
            return "Invalid re-entered password";
        return "";
    };
    return ValidationService;
}());
ValidationService = __decorate([
    core_1.Injectable()
], ValidationService);
exports.ValidationService = ValidationService;
//# sourceMappingURL=validation.service.js.map