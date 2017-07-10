import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }   from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { AppRouteComponent } from './route-app.component';
import { AppComponent }   from './app.component';
import { SignInFormComponent } from './sign-in-form.component';
import { SignUpFormComponent } from './sign-up-form.component';
import { UserListComponent } from './user-list.component';
import { TrailHomeComponent } from './trail-home.component';
import { GeObjectComponent } from './geobject.component';

import { TrailEntityComponent } from './trail-entity.component';

import { MainInfoComponent } from './trail-config.components/main-info.component';
import { PhotosComponent } from './trail-config.components/photos.component';
import { DescriptionComponent } from './trail-config.components/description.component';
import { RouteMarksComponent } from './trail-config.components/route-marks.component';

import { FileUploaderComponent } from './file-uploader.component';
import { TrailPhotoUploaderComponent } from './trail-photo-uploader.component';

import { AuthGuardService } from './auth-guard.service';
import { DataService } from './data.service';
import { HttpService } from './http.service';
import { ValidationService } from './validation.service';
import { TrailDataService } from './trail-data.service';

const appRoutes: Routes = [
  { path: 'userlist', component: UserListComponent, canActivate: [AuthGuardService] },
  { path: 'trailentity', component: TrailEntityComponent },
  { path: '', component: AppComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
    imports:      [ BrowserModule, FormsModule, HttpModule, RouterModule.forRoot(appRoutes) ],
    declarations: [ AppRouteComponent, AppComponent, SignInFormComponent, SignUpFormComponent, 
            				UserListComponent, FileUploaderComponent, TrailHomeComponent, GeObjectComponent, 
            				TrailEntityComponent, TrailPhotoUploaderComponent, DescriptionComponent,
            				MainInfoComponent, PhotosComponent, RouteMarksComponent ],
    bootstrap:    [ AppRouteComponent ],
    providers: [ AuthGuardService, HttpService, DataService, TrailDataService ]
})
export class AppModule { 
}