import { Component } from '@angular/core';

import { TrailDataService } from '../trail-data.service';

@Component({
    selector: 'photos',
	templateUrl: 'trail-config.components/photos.component.html'
})

export class PhotosComponent {

	constructor(private tds:TrailDataService){ }

	deleteImage(item:number){
		let i=this.tds.trailPhotos.findIndex(x => x.index === item)
		this.tds.trailPhotos.splice(i, 1);
	}
}