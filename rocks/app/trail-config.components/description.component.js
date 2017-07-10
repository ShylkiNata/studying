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
var DescriptionComponent = (function () {
    function DescriptionComponent(tds) {
        this.tds = tds;
        this.url = '';
        this.title = '';
        this.refVisibility = false;
    }
    DescriptionComponent.prototype.deleteReference = function (item) {
        var i = this.tds.trailReferences.findIndex(function (x) { return x.index === item; });
        this.tds.trailReferences.splice(i, 1);
        if (this.tds.trailReferences.length == 0)
            this.refVisibility = false;
    };
    DescriptionComponent.prototype.addReference = function () {
        if (this.title == "" || this.url == "")
            this.tds.error = {
                body: "Please, fill both of fields to add reference",
                flag: true
            };
        else {
            this.tds.trailReferences.push({ index: this.tds.trailReferences.length,
                name: this.title,
                path: 'http://' + this.url });
            this.url = '';
            this.title = '';
            this.refVisibility = true;
        }
    };
    return DescriptionComponent;
}());
DescriptionComponent = __decorate([
    core_1.Component({
        selector: 'description',
        templateUrl: 'trail-config.components/description.component.html'
    }),
    __metadata("design:paramtypes", [trail_data_service_1.TrailDataService])
], DescriptionComponent);
exports.DescriptionComponent = DescriptionComponent;
//# sourceMappingURL=description.component.js.map