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
var trail_data_service_1 = require("./trail-data.service");
var TrailPhotoUploaderComponent = (function () {
    function TrailPhotoUploaderComponent(tds) {
        this.tds = tds;
        this.activeColor = '#f6d21d';
        this.baseColor = ' #f6d21d';
        this.overlayColor = 'rgba(255,255,255,0.5)';
        this.iconColor = '';
        this.dragging = false;
        this.loaded = false;
        this.imageLoaded = false;
        this.imageSrc = '';
        this.borderColor = '#f6d21d';
    }
    TrailPhotoUploaderComponent.prototype.handleDragEnter = function () {
        this.dragging = true;
    };
    TrailPhotoUploaderComponent.prototype.handleDragLeave = function () {
        this.dragging = false;
    };
    TrailPhotoUploaderComponent.prototype.handleDrop = function (e) {
        e.preventDefault();
        this.dragging = false;
        this.handleInputChange(e);
    };
    TrailPhotoUploaderComponent.prototype.handleImageLoad = function () {
        this.imageLoaded = true;
        this.iconColor = this.overlayColor;
    };
    TrailPhotoUploaderComponent.prototype.handleInputChange = function (e) {
        var file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];
        var pattern = /image-*/;
        var reader = new FileReader();
        if (!file.type.match(pattern)) {
            alert('invalid format');
            return;
        }
        this.loaded = false;
        reader.onload = this._handleReaderLoaded.bind(this);
        reader.readAsDataURL(file);
    };
    TrailPhotoUploaderComponent.prototype._handleReaderLoaded = function (e) {
        var reader = e.target;
        this.imageSrc = reader.result; // our loaded image
        this.loaded = true;
        if (this.calledBy == 'trail') {
            this.tds.trailPhotos.push({ index: this.tds.trailPhotos.length,
                image: this.imageSrc });
            this.imageSrc = '';
            this.loaded = false;
        }
        else {
            this.tds.coverPhoto = this.imageSrc;
            this.tds.hideCoverLoadButton = false;
        }
    };
    TrailPhotoUploaderComponent.prototype._setActive = function () {
        this.borderColor = this.activeColor;
        if (this.imageSrc.length === 0) {
            this.iconColor = this.activeColor;
        }
    };
    TrailPhotoUploaderComponent.prototype._setInactive = function () {
        this.borderColor = this.baseColor;
        if (this.imageSrc.length === 0) {
            this.iconColor = this.baseColor;
        }
    };
    return TrailPhotoUploaderComponent;
}());
TrailPhotoUploaderComponent = __decorate([
    core_1.Component({
        selector: 'trail-photo-uploader',
        templateUrl: 'trail-config.components/trail-photo-uploader.component.html',
        inputs: ['calledBy', 'baseColor', 'overlayColor', 'imgSource', 'hideLoadButton']
    }),
    __metadata("design:paramtypes", [trail_data_service_1.TrailDataService])
], TrailPhotoUploaderComponent);
exports.TrailPhotoUploaderComponent = TrailPhotoUploaderComponent;
//# sourceMappingURL=trail-photo-uploader.component.js.map