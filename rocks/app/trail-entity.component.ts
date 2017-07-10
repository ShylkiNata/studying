import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { Response} from '@angular/http';

import { HttpService } from './http.service';
import { DataService } from './data.service';
import { TrailDataService } from './trail-data.service';

import { ConfigTabStructure } from './dataStructures';

import { MainInfoComponent } from './trail-config.components/main-info.component';
import { PhotosComponent } from './trail-config.components/photos.component';
import { DescriptionComponent } from './trail-config.components/description.component';

@Component({
    selector: 'trailEntity',
	templateUrl: 'trail-entity.component.html'
})

export class TrailEntityComponent {
	tabNames:string[]=['Main info','Route marks','Photos','Description','Additional info'];

	tabs:ConfigTabStructure[]=[];

	previousTab:number=0;

	next:string='';
	back:string='';
	nextState:boolean=false;
	backState:boolean=false;

	constructor( private http: HttpService,
				 private tds: TrailDataService ){

		for(let i=0; i<this.tabNames.length; i++)
			this.tabs.push({ number: i,
							 name: this.tabNames[i],
							 color: '',
							 visibility: false });

		this.tabs[0]['color']='2px solid #20822d';
		this.tabs[0]['visibility']=true;

		this.next=this.tabNames[1];
		this.nextState=true;
	}

	changeTab(index: number){
		this.tabs[this.previousTab]['color']='';
		this.tabs[this.previousTab]['visibility']=false;

		this.previousTab=index;
		this.tabs[index]['color']='2px solid #20822d';
		this.tabs[index]['visibility']=true;

		if(index+1==this.tabNames.length)  {
			this.next='';
			this.nextState=false;
		}
		else {
			this.next=this.tabNames[index+1];	
			this.nextState=true;
		}

		if(index-1<0) {
			this.back='';
			this.backState=false;
		}
		else {
			this.back=this.tabNames[index-1];
			this.backState=true;
		}
	}

	closeAlert(){
		this.tds.error = { body:"", flag:false }
	}
}