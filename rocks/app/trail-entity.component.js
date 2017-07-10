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
var trail_data_service_1 = require("./trail-data.service");
var TrailEntityComponent = (function () {
    function TrailEntityComponent(http, tds) {
        this.http = http;
        this.tds = tds;
        this.tabNames = ['Main info', 'Route marks', 'Photos', 'Description', 'Additional info'];
        this.tabs = [];
        this.previousTab = 0;
        this.next = '';
        this.back = '';
        this.nextState = false;
        this.backState = false;
        for (var i = 0; i < this.tabNames.length; i++)
            this.tabs.push({ number: i,
                name: this.tabNames[i],
                color: '',
                visibility: false });
        this.tabs[0]['color'] = '2px solid #20822d';
        this.tabs[0]['visibility'] = true;
        this.next = this.tabNames[1];
        this.nextState = true;
    }
    TrailEntityComponent.prototype.changeTab = function (index) {
        this.tabs[this.previousTab]['color'] = '';
        this.tabs[this.previousTab]['visibility'] = false;
        this.previousTab = index;
        this.tabs[index]['color'] = '2px solid #20822d';
        this.tabs[index]['visibility'] = true;
        if (index + 1 == this.tabNames.length) {
            this.next = '';
            this.nextState = false;
        }
        else {
            this.next = this.tabNames[index + 1];
            this.nextState = true;
        }
        if (index - 1 < 0) {
            this.back = '';
            this.backState = false;
        }
        else {
            this.back = this.tabNames[index - 1];
            this.backState = true;
        }
    };
    TrailEntityComponent.prototype.closeAlert = function () {
        this.tds.error = { body: "", flag: false };
    };
    return TrailEntityComponent;
}());
TrailEntityComponent = __decorate([
    core_1.Component({
        selector: 'trailEntity',
        templateUrl: 'trail-entity.component.html'
    }),
    __metadata("design:paramtypes", [http_service_1.HttpService,
        trail_data_service_1.TrailDataService])
], TrailEntityComponent);
exports.TrailEntityComponent = TrailEntityComponent;
//# sourceMappingURL=trail-entity.component.js.map