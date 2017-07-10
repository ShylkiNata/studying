import { Component, OnInit } from '@angular/core';

import { TrailDataService } from './trail-data.service';

@Component({
    selector: 'trail-photo-uploader',
    templateUrl: 'trail-config.components/trail-photo-uploader.component.html',
    inputs:['calledBy','baseColor','overlayColor','imgSource','hideLoadButton']
})

export class TrailPhotoUploaderComponent {
    
    activeColor: string = '#f6d21d';
    baseColor: string = ' #f6d21d';
    overlayColor: string = 'rgba(255,255,255,0.5)';
    iconColor:string = '';
    
    dragging: boolean = false;
    loaded: boolean = false;
    imageLoaded: boolean = false;
    imageSrc: string = '';
    borderColor: string = '#f6d21d';

    constructor( private tds: TrailDataService ) { }
    
    handleDragEnter() {
        this.dragging = true;
    }
    handleDragLeave() {
        this.dragging = false;
    }
    
    handleDrop(e) {
        e.preventDefault();
        this.dragging = false;
        this.handleInputChange(e);
    }
    
    handleImageLoad() {
        this.imageLoaded = true;
        this.iconColor = this.overlayColor;
    }

    handleInputChange(e) {
        var file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];

        var pattern = /image-*/;
        var reader = new FileReader();

        if (!file.type.match(pattern)) {
            alert('invalid format');
            return;
        }

        this.loaded = false;

        reader.onload = this._handleReaderLoaded.bind(this);
        reader.readAsDataURL(file);
    }
    
    _handleReaderLoaded(e) {
        var reader = e.target;
        this.imageSrc = reader.result; // our loaded image
        this.loaded = true;  

        if(this.calledBy=='trail') {
            this.tds.trailPhotos.push({ index: this.tds.trailPhotos.length,
                                        image: this.imageSrc });
            this.imageSrc = '';
            this.loaded = false;
        }
        else {
            this.tds.coverPhoto = this.imageSrc;
            this.tds.hideCoverLoadButton=false;
        }
    }
    
    _setActive() {
        this.borderColor = this.activeColor;
        if (this.imageSrc.length === 0) {
            this.iconColor = this.activeColor;
        }
    }
    
    _setInactive() {
        this.borderColor = this.baseColor;
        if (this.imageSrc.length === 0) {
            this.iconColor = this.baseColor;
        }
    }    
}