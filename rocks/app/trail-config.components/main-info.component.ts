import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { Response} from '@angular/http';

//import { HttpService } from '../http.service';
import { FileService } from '../file.service';
import { TrailDataService } from '../trail-data.service';

import { CurrentOption } from '../dataStructures';

@Component({
    selector: 'mainInfo',
	templateUrl: 'trail-config.components/main-info.component.html',
	providers: [ FileService ]
})

export class MainInfoComponent {
	whyGo:string = "Explain briefly key feature or two for this trail and why it is worth visiting by other hiker. Just imagine you are making a point to you friend why he should hike it.";

	color:string="#20822d";
	checked:number[]=[1,2,3,0]; // checked options for complexity, type...

	constructor( private file: FileService,
		 		 private tds: TrailDataService ){

		if(this.tds.mainOptions.length==0)
			this.file.getData("trailOptions.json")
	                 .subscribe((data: Response) => this.tds.mainOptions=data.json() );
	}

	activateOption(key:string, position:number){
		let i=0;


		switch(key){
			case 'Complexity': 
					this.tds.trailEntity.complexity=position;
				break;
			case 'Trail type': {
					i=1;
					this.tds.trailEntity.trailType=position;
				}
				break;
			case 'Duration': {
					i=2;
					this.tds.trailEntity.duration=position;
				}
				break;
			case 'Additional activities': {
					i=3;
					if(this.tds.mainOptions[i]['value'][position-1]['color']=='') {
						this.tds.mainOptions[i]['value'][position-1]['color']='#20822d';
						this.checked[i]=position;
					}
					else {
						this.tds.mainOptions[i]['value'][position-1]['color']='';
						this.checked[i]=0; // not checked
					}
				}
				break;
		}
		if(i!=3){
			this.tds.mainOptions[i]['value'][this.checked[i]-1]['color']='';
			this.tds.mainOptions[i]['value'][position-1]['color']='#20822d';
			this.checked[i]=position;
		}
	}
}