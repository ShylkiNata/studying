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
var router_1 = require("@angular/router");
var http_service_1 = require("./http.service");
var file_service_1 = require("./file.service");
var data_service_1 = require("./data.service");
var auth_guard_service_1 = require("./auth-guard.service");
var UserListComponent = (function () {
    function UserListComponent(http, file, guard, currentData, router) {
        this.http = http;
        this.file = file;
        this.guard = guard;
        this.currentData = currentData;
        this.router = router;
        this.userList = [];
        this.newUser = { email: "",
            password: "",
            repassword: "",
            role: "user",
            avatar: "" };
        this.roleStructure = ["user", "premium user", "admin"];
        this.User = { email: "",
            password: "",
            role: "",
            avatar: "" };
        this.Error = {
            body: "",
            flag: false
        };
        this.bufferEmail = "";
        this.defaultAvatar = "";
        this.emailCurrentState = "";
        this.formHeader = "";
        this.formOption = "";
        this.shadowIsHidden = false;
    }
    UserListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.http.postData({ action: 3 }) // select all users from database
            .subscribe(function (data) { return _this.normalizeStructure(data); }, function (error) { return _this.Error = {
            body: error,
            flag: true
        }; });
        this.file.getData("defaultAvatar.json")
            .subscribe(function (data) { return _this.defaultAvatar = (data.json()).toString(); });
        this.newUser["avatar"] = this.defaultAvatar;
        this.User = this.currentData.authorizedUser;
        this.closeAlert();
    };
    UserListComponent.prototype.normalizeStructure = function (answer) {
        if (answer['error'].length === 0)
            for (var i = 0; i < answer['success'].length; i++) {
                this.userList.push({ email: answer['success'][i]['email'],
                    password: answer['success'][i]['password'],
                    role: answer['success'][i]['role'],
                    avatar: answer['success'][i]['avatar'] });
            }
        else
            this.Error = {
                body: answer['error'],
                flag: true
            };
    };
    UserListComponent.prototype.showAddUserForm = function (event, keyWord) {
        if (keyWord === 'Edit') {
            var target = event.target || event.srcElement || event.currentTarget;
            var id_1 = target.attributes.id.nodeValue; // id элемента по email
            var position = this.userList.indexOf(this.userList.filter(function (val) {
                return val.email === id_1;
            })[0]);
            if (((this.User['role'] === "user" || this.User['role'] === "premium user") && this.User['email'] === id_1) || (this.User['role'] === "admin")) {
                this.formHeader = "Edit user profile";
                this.newUser = { email: this.userList[position]['email'],
                    password: this.userList[position]['password'],
                    repassword: this.userList[position]['password'],
                    role: this.userList[position]['role'],
                    avatar: this.userList[position]['avatar'] };
                this.emailCurrentState = this.userList[position]['email'];
                this.currentData.bufAvatar = this.userList[position]['avatar'];
                this.bufferEmail = this.userList[position]['email'];
                this.shadowIsHidden = true;
                this.formOption = keyWord;
            }
            else
                this.Error = {
                    body: "Error! Unfortunately, you don't have permissions to delete or edit other user data",
                    flag: true
                };
        }
        else if (this.User['role'] === "admin") {
            this.formHeader = "Add new user";
            this.newUser = { email: "",
                password: "",
                repassword: "",
                role: "user",
                avatar: this.defaultAvatar };
            this.currentData.bufAvatar = this.defaultAvatar;
            this.shadowIsHidden = true; // turn on button to add user
            this.formOption = keyWord;
        }
    };
    UserListComponent.prototype.deleteUser = function (event) {
        var _this = this;
        if (this.User['role'] != "user" && this.User['role'] != "premium user") {
            var target = event.target || event.srcElement || event.currentTarget;
            var id_2 = target.attributes.id.nodeValue; // id элемента по email
            var position_1 = this.userList.indexOf(this.userList.filter(function (val) {
                return val.email === id_2;
            })[0]); // element index in the data structure
            if (this.userList[position_1]['role'] != 'admin')
                this.http.postData({ action: 4, email: id_2 })
                    .subscribe(function (data) { return _this.hearResponse(data, position_1); }, function (error) { return _this.Error = {
                    body: error,
                    flag: true
                }; });
            else
                this.Error = { body: "You can't remove the administrator.",
                    flag: true };
        }
        else
            this.Error = {
                body: "Error! Unfortunately, you don't have permissions to delete or edit user data",
                flag: true
            };
    };
    UserListComponent.prototype.hearResponse = function (answer, index) {
        if (answer['error'].length === 0) {
            this.userList.splice(index, 1); // delete element
        }
        else
            this.Error = {
                body: answer['error'],
                flag: true
            };
    };
    UserListComponent.prototype.closeSignForm = function () {
        if (this.shadowIsHidden)
            this.shadowIsHidden = false;
        else
            this.shadowIsHidden = true;
    };
    UserListComponent.prototype.getResult = function (answer, action) {
        if (answer['error'].length === 0) {
            if (action == 2)
                this.userList.push({ email: this.newUser['email'],
                    password: this.newUser['password'],
                    role: this.newUser['role'],
                    avatar: this.newUser['avatar'] });
            else {
                var id_3 = this.bufferEmail;
                var index = this.userList.indexOf(this.userList.filter(function (val) {
                    return val.email === id_3;
                })[0]); // позиция элемента в структуре данных
                this.userList[index] = { email: this.newUser['email'],
                    password: this.newUser['password'],
                    role: this.newUser['role'],
                    avatar: this.newUser['avatar'] };
                if (this.bufferEmail === this.User['email']) {
                    this.currentData.authorizedUser = { email: this.newUser['email'],
                        password: this.newUser['password'],
                        role: this.newUser['role'],
                        avatar: this.newUser['avatar'] };
                    this.User = this.currentData.authorizedUser;
                    this.bufferEmail = "";
                }
            }
            this.closeSignForm();
        }
        else
            this.Error = {
                body: answer['error'],
                flag: true
            };
    };
    UserListComponent.prototype.addNewUser = function () {
        var _this = this;
        if (this.newUser['password'] != this.newUser['repassword'])
            this.Error = {
                body: "The entered passwords do not match",
                flag: true
            };
        else if (this.newUser['email'] == "" || this.newUser['password'] == "")
            this.Error = {
                body: "Please, fill all fields",
                flag: true
            };
        else {
            var seqNumber_1 = 2; // add
            if (this.formHeader != "Add new user")
                seqNumber_1 = 5; // edit
            var id_4 = this.newUser['email'];
            var index = this.userList.indexOf(this.userList.filter(function (val) {
                return val.email === id_4;
            })[0]);
            if (index == -1 || (this.emailCurrentState == this.newUser['email'])) {
                this.newUser['avatar'] = this.currentData.bufAvatar;
                this.http.postData({ action: seqNumber_1, email: this.newUser['email'], password: this.newUser['password'], role: this.newUser['role'], avatar: this.newUser['avatar'], additional: this.bufferEmail })
                    .subscribe(function (data) { return _this.getResult(data, seqNumber_1); }, function (error) { return _this.Error = {
                    body: error,
                    flag: true
                }; });
            }
            else
                this.Error = {
                    body: "This email is already in use",
                    flag: true
                };
        }
    };
    UserListComponent.prototype.logOut = function () {
        this.currentData.authorizedUser = { email: "",
            password: "",
            role: "",
            avatar: "" };
        this.closeAlert();
        this.guard.can = false;
        this.router.navigate(['']);
    };
    UserListComponent.prototype.closeAlert = function () {
        this.Error = {
            body: "",
            flag: false
        };
    };
    return UserListComponent;
}());
UserListComponent = __decorate([
    core_1.Component({
        selector: 'user-list',
        templateUrl: 'user-list.component.html',
        providers: [file_service_1.FileService]
    }),
    __metadata("design:paramtypes", [http_service_1.HttpService,
        file_service_1.FileService,
        auth_guard_service_1.AuthGuardService,
        data_service_1.DataService,
        router_1.Router])
], UserListComponent);
exports.UserListComponent = UserListComponent;
//# sourceMappingURL=user-list.component.js.map