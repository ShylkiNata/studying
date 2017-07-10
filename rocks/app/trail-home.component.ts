import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { Response} from '@angular/http';

import { HttpService } from './http.service';
import { DataService } from './data.service';

import { MenuStructure, UserStructure, SignInStructure, SignUpStructure, ErrorStructure } from './dataStructures';

@Component({
    selector: 'trailHome',
	templateUrl: 'trail-home.component.html'
})

export class TrailHomeComponent {

	constructor( private http: HttpService, 
			     private currentData: DataService ){ }

	showFound:boolean = false;
	location:string = "";
	foundRoutes:any[];

	searchLocation(){
		if(this.location.length>0) 
			this.http.postData( {action: 6, location: this.location } ) 
					 .subscribe( data => this.showFoundTrails(data) );
		else this.showFound=false;;

	}

	showFoundTrails(data){
		if(data['success'].length>0) {
			this.foundRoutes=data['success'];
			this.showFound=true;
		}
		else this.showFound=false;
	}
}