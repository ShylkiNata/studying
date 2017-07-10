"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var validation_service_1 = require("./validation.service");
var SignInFormComponent = (function () {
    function SignInFormComponent(validation) {
        this.validation = validation;
        this.data = {
            email: "",
            password: ""
        };
        this.error = {
            email: "",
            password: ""
        };
        this.emailColor = "#6495ed";
        this.passwordColor = "#6495ed";
        this.outgoingData = new core_1.EventEmitter();
    }
    SignInFormComponent.prototype.sendData = function (event, pointer) {
        switch (pointer) {
            case 'email':
                this.error['email'] = this.validation.checkEmail(this.data['email']);
                if (this.error['email'].length != 0)
                    this.emailColor = "#fe2e2e";
                else
                    this.emailColor = "#0b610b";
                break;
            case 'password':
                this.error['password'] = this.validation.checkPassword(this.data['password']);
                if (this.error['password'].length != 0)
                    this.passwordColor = "#fe2e2e";
                else
                    this.passwordColor = "#0b610b";
                break;
        }
        if (this.data['email'].length != 0 && this.data['password'].length != 0)
            this.outgoingData.emit(this.data);
    };
    return SignInFormComponent;
}());
__decorate([
    core_1.Output('userData'),
    __metadata("design:type", Object)
], SignInFormComponent.prototype, "outgoingData", void 0);
SignInFormComponent = __decorate([
    core_1.Component({
        selector: 'sign-in-form',
        templateUrl: 'sign-in-form.component.html',
        providers: [validation_service_1.ValidationService]
    }),
    __metadata("design:paramtypes", [validation_service_1.ValidationService])
], SignInFormComponent);
exports.SignInFormComponent = SignInFormComponent;
//# sourceMappingURL=sign-in-form.component.js.map