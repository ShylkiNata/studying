"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var forms_1 = require("@angular/forms");
var http_1 = require("@angular/http");
var router_1 = require("@angular/router");
var route_app_component_1 = require("./route-app.component");
var app_component_1 = require("./app.component");
var sign_in_form_component_1 = require("./sign-in-form.component");
var sign_up_form_component_1 = require("./sign-up-form.component");
var user_list_component_1 = require("./user-list.component");
var trail_home_component_1 = require("./trail-home.component");
var geobject_component_1 = require("./geobject.component");
var trail_entity_component_1 = require("./trail-entity.component");
var main_info_component_1 = require("./trail-config.components/main-info.component");
var photos_component_1 = require("./trail-config.components/photos.component");
var description_component_1 = require("./trail-config.components/description.component");
var route_marks_component_1 = require("./trail-config.components/route-marks.component");
var file_uploader_component_1 = require("./file-uploader.component");
var trail_photo_uploader_component_1 = require("./trail-photo-uploader.component");
var auth_guard_service_1 = require("./auth-guard.service");
var data_service_1 = require("./data.service");
var http_service_1 = require("./http.service");
var trail_data_service_1 = require("./trail-data.service");
var appRoutes = [
    { path: 'userlist', component: user_list_component_1.UserListComponent, canActivate: [auth_guard_service_1.AuthGuardService] },
    { path: 'trailentity', component: trail_entity_component_1.TrailEntityComponent },
    { path: '', component: app_component_1.AppComponent },
    { path: '**', redirectTo: '' }
];
var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    core_1.NgModule({
        imports: [platform_browser_1.BrowserModule, forms_1.FormsModule, http_1.HttpModule, router_1.RouterModule.forRoot(appRoutes)],
        declarations: [route_app_component_1.AppRouteComponent, app_component_1.AppComponent, sign_in_form_component_1.SignInFormComponent, sign_up_form_component_1.SignUpFormComponent,
            user_list_component_1.UserListComponent, file_uploader_component_1.FileUploaderComponent, trail_home_component_1.TrailHomeComponent, geobject_component_1.GeObjectComponent,
            trail_entity_component_1.TrailEntityComponent, trail_photo_uploader_component_1.TrailPhotoUploaderComponent, description_component_1.DescriptionComponent,
            main_info_component_1.MainInfoComponent, photos_component_1.PhotosComponent, route_marks_component_1.RouteMarksComponent],
        bootstrap: [route_app_component_1.AppRouteComponent],
        providers: [auth_guard_service_1.AuthGuardService, http_service_1.HttpService, data_service_1.DataService, trail_data_service_1.TrailDataService]
    })
], AppModule);
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map