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
var http_service_1 = require("./http.service");
var data_service_1 = require("./data.service");
var auth_guard_service_1 = require("./auth-guard.service");
var file_service_1 = require("./file.service");
var AppComponent = (function () {
    function AppComponent(http, guard, currentData, file) {
        this.http = http;
        this.guard = guard;
        this.currentData = currentData;
        this.file = file;
        this.User = { email: "",
            password: "",
            role: "",
            avatar: "" };
        this.defaultAvatar = "";
        this.Error = {
            body: "",
            flag: false
        };
        this.shadowIsHidden = false;
        this.authorized = false;
        this.showMenu = false;
        this.colorInbg = "";
        this.colorUpbg = "";
        this.colorIn = "";
        this.colorUp = "";
        this.signButton = "";
        this.signInType = false;
        this.signUpType = false;
        this.sign = "Join Us";
    }
    AppComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.file.getData("menuItems.json")
            .subscribe(function (data) { return _this.menuItems = data.json(); });
        this.file.getData("defaultAvatar.json")
            .subscribe(function (data) { return _this.defaultAvatar = (data.json()).toString(); }); // without toString: Failed to load resource: net::ERR_UNKNOWN_URL_SCHEME
        this.User = this.currentData.authorizedUser;
        if (this.User['email'] != "") {
            this.closeSignForm();
            this.authorized = true;
            this.guard.can = true;
        }
        this.closeAlert();
    };
    AppComponent.prototype.openSignForm = function (flag) {
        this.shadowIsHidden = true;
        this.changeState(flag);
    };
    AppComponent.prototype.closeSignForm = function () {
        this.shadowIsHidden = false;
        this.signButton = '';
        this.signInType = false;
        this.signUpType = false;
    };
    AppComponent.prototype.changeState = function (flag) {
        if (flag == 1) {
            this.colorUpbg = "#fff";
            this.colorInbg = "";
            this.colorUp = "#2e2e2e";
            this.colorIn = "";
            this.signButton = "CREATE ACCOUNT";
            this.signUpType = true;
            this.signInType = false;
        }
        else {
            this.colorUpbg = "";
            this.colorInbg = "#fff";
            this.colorUp = "";
            this.colorIn = "#2e2e2e";
            this.signButton = "sign in";
            this.signInType = true;
            this.signUpType = false;
        }
    };
    AppComponent.prototype.handleData = function (userData) {
        this.receivedData = userData;
    };
    AppComponent.prototype.handleRegData = function (userRegData) {
        this.receivedRegData = userRegData;
    };
    AppComponent.prototype.checkUserData = function () {
        var _this = this;
        if (this.signButton == "sign in") {
            this.User['email'] = this.receivedData['email'];
            this.User['password'] = this.receivedData['password'];
            this.http.postData({ action: 1, email: this.receivedData['email'], password: this.receivedData['password'] })
                .subscribe(function (data) { return _this.getAnswer(data, 1); }, function (error) { return _this.Error = {
                body: error,
                flag: true
            }; });
            this.sign = "Sign Out";
        }
        else {
            if (this.receivedRegData['email'] != "" && this.receivedRegData['password'] != "" && this.receivedRegData['retype'] != "" && this.receivedRegData['password'] == this.receivedRegData['retype']) {
                this.User['email'] = this.receivedRegData['email'];
                this.User['password'] = this.receivedRegData['password'];
                this.User['avatar'] = this.defaultAvatar;
                this.http.postData({ action: 2, email: this.receivedRegData['email'], password: this.receivedRegData['password'], role: "user", avatar: this.defaultAvatar })
                    .subscribe(function (data) { return _this.getAnswer(data, 2); }, function (error) { return _this.Error = {
                    body: error,
                    flag: true
                }; });
                this.sign = "Sign Out";
            }
            else
                this.Error = {
                    body: "Please, fill the fields coorectly",
                    flag: true
                };
        }
    };
    AppComponent.prototype.logOut = function () {
        this.authorized = false;
        this.User = { email: "",
            password: "",
            role: "",
            avatar: "" };
        this.receivedData = { email: "",
            password: "" };
        this.receivedRegData = { email: "",
            password: "",
            retype: "" };
        this.guard.can = false;
        this.currentData.authorizedUser = { email: "",
            password: "",
            role: "",
            avatar: "" };
        this.currentData.bufAvatar = "";
        this.Error = {
            body: "",
            flag: false
        };
        this.sign = "Join Us";
    };
    AppComponent.prototype.getAnswer = function (answer, action) {
        if (answer['error'].length === 0) {
            this.closeSignForm();
            this.authorized = true;
            this.guard.can = true;
            this.currentData.authorizedUser['email'] = this.User['email'];
            this.currentData.authorizedUser['password'] = this.User['password'];
            if (action == 1) {
                this.User['role'] = answer['success']['role'];
                this.User['avatar'] = answer['success']['avatar'];
            }
            else
                this.User['role'] = answer['success'];
            this.currentData.authorizedUser['avatar'] = this.User['avatar'];
            this.currentData.authorizedUser['role'] = this.User['role'];
        }
        else
            this.Error = {
                body: answer['error'],
                flag: true
            };
    };
    AppComponent.prototype.showMenuList = function () {
        if (this.showMenu)
            this.showMenu = false;
        else
            this.showMenu = true;
    };
    AppComponent.prototype.closeAlert = function () {
        this.Error = {
            body: "",
            flag: false
        };
    };
    return AppComponent;
}());
AppComponent = __decorate([
    core_1.Component({
        selector: 'my-app',
        templateUrl: 'main-page.html',
        providers: [/*HttpService,*/ file_service_1.FileService]
    }),
    __metadata("design:paramtypes", [http_service_1.HttpService,
        auth_guard_service_1.AuthGuardService,
        data_service_1.DataService,
        file_service_1.FileService])
], AppComponent);
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map