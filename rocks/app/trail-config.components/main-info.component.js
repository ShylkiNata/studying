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
//import { HttpService } from '../http.service';
var file_service_1 = require("../file.service");
var trail_data_service_1 = require("../trail-data.service");
var MainInfoComponent = (function () {
    function MainInfoComponent(file, tds) {
        var _this = this;
        this.file = file;
        this.tds = tds;
        this.whyGo = "Explain briefly key feature or two for this trail and why it is worth visiting by other hiker. Just imagine you are making a point to you friend why he should hike it.";
        this.color = "#20822d";
        this.checked = [1, 2, 3, 0]; // checked options for complexity, type...
        if (this.tds.mainOptions.length == 0)
            this.file.getData("trailOptions.json")
                .subscribe(function (data) { return _this.tds.mainOptions = data.json(); });
    }
    MainInfoComponent.prototype.activateOption = function (key, position) {
        var i = 0;
        switch (key) {
            case 'Complexity':
                this.tds.trailEntity.complexity = position;
                break;
            case 'Trail type':
                {
                    i = 1;
                    this.tds.trailEntity.trailType = position;
                }
                break;
            case 'Duration':
                {
                    i = 2;
                    this.tds.trailEntity.duration = position;
                }
                break;
            case 'Additional activities':
                {
                    i = 3;
                    if (this.tds.mainOptions[i]['value'][position - 1]['color'] == '') {
                        this.tds.mainOptions[i]['value'][position - 1]['color'] = '#20822d';
                        this.checked[i] = position;
                    }
                    else {
                        this.tds.mainOptions[i]['value'][position - 1]['color'] = '';
                        this.checked[i] = 0; // not checked
                    }
                }
                break;
        }
        if (i != 3) {
            this.tds.mainOptions[i]['value'][this.checked[i] - 1]['color'] = '';
            this.tds.mainOptions[i]['value'][position - 1]['color'] = '#20822d';
            this.checked[i] = position;
        }
    };
    return MainInfoComponent;
}());
MainInfoComponent = __decorate([
    core_1.Component({
        selector: 'mainInfo',
        templateUrl: 'trail-config.components/main-info.component.html',
        providers: [file_service_1.FileService]
    }),
    __metadata("design:paramtypes", [file_service_1.FileService,
        trail_data_service_1.TrailDataService])
], MainInfoComponent);
exports.MainInfoComponent = MainInfoComponent;
//# sourceMappingURL=main-info.component.js.map