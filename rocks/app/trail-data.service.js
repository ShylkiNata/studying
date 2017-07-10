"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var dataStructures_1 = require("./dataStructures");
var TrailDataService = (function () {
    function TrailDataService() {
        this.coverPhoto = '';
        this.trailPhotos = [];
        this.trailReferences = [];
        this.hideCoverLoadButton = true;
        this.error = { body: "", flag: false };
        this.trailEntity = new dataStructures_1.TrailEntity();
        this.mainOptions = [];
    }
    return TrailDataService;
}());
TrailDataService = __decorate([
    core_1.Injectable()
], TrailDataService);
exports.TrailDataService = TrailDataService;
//# sourceMappingURL=trail-data.service.js.map