import { Injectable, Component, NgModule } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable()
export class AuthGuardService implements CanActivate {
  
  can: boolean=false; ///FALSE
  
  constructor(private router: Router){ }

  canActivate() {
  	if(!this.can)
       this.router.navigate(['']); // not authorized - go to the main page
    return this.can;
  }
}