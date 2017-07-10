import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { Response} from '@angular/http';

import { HttpService } from './http.service';
import { DataService } from './data.service';

import { MenuStructure, UserStructure, SignInStructure, SignUpStructure, ErrorStructure } from './dataStructures';

@Component({
    selector: 'geobject',
	templateUrl: 'geobject.component.html'
})

export class GeObjectComponent {

	constructor( private http: HttpService, 
			     private currentData: DataService ){ }

}