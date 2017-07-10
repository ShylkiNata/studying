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
var trail_data_service_1 = require("../trail-data.service");
var PhotosComponent = (function () {
    function PhotosComponent(tds) {
        this.tds = tds;
    }
    PhotosComponent.prototype.deleteImage = function (item) {
        var i = this.tds.trailPhotos.findIndex(function (x) { return x.index === item; });
        this.tds.trailPhotos.splice(i, 1);
    };
    return PhotosComponent;
}());
PhotosComponent = __decorate([
    core_1.Component({
        selector: 'photos',
        templateUrl: 'trail-config.components/photos.component.html'
    }),
    __metadata("design:paramtypes", [trail_data_service_1.TrailDataService])
], PhotosComponent);
exports.PhotosComponent = PhotosComponent;
//# sourceMappingURL=photos.component.js.map