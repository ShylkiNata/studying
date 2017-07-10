import { Injectable } from '@angular/core';

import { TrailPhotos,TrailReference, ErrorStructure, TrailEntity } from './dataStructures'
 
@Injectable()
export class TrailDataService{

	coverPhoto:string='';
	trailPhotos:TrailPhotos[] = [];

	trailReferences:TrailReference[] = [];

	hideCoverLoadButton=true;

	error:ErrorStructure = { body: "", flag: false };

	trailEntity: TrailEntity = new TrailEntity();

	mainOptions:Object[]=[];
}