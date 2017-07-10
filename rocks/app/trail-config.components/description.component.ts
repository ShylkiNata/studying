import { Component } from '@angular/core';

import { TrailDataService } from '../trail-data.service';

@Component({
    selector: 'description',
	templateUrl: 'trail-config.components/description.component.html'
})

export class DescriptionComponent {
	url:string = '';
	title:string = '';
	refVisibility:boolean = false;

	constructor(private tds:TrailDataService){ }

	deleteReference(item:number){
		let i=this.tds.trailReferences.findIndex(x => x.index === item)
		this.tds.trailReferences.splice(i, 1);

		if(this.tds.trailReferences.length==0)
			this.refVisibility=false;
	}
	addReference(){
		if(this.title=="" || this.url=="") 
			this.tds.error = {
				body: "Please, fill both of fields to add reference",
				flag: true
			}
		else {
			this.tds.trailReferences.push({ index: this.tds.trailReferences.length,
											name:  this.title,
											path:  'http://'+this.url });
			this.url='';
			this.title='';

			this.refVisibility = true;
		}
	}
}