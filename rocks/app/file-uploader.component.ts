import { Component, OnInit } from '@angular/core';

import { HttpService } from './http.service';
import { DataService } from './data.service';

@Component({
    selector: 'file-uploader',
    templateUrl: 'file-uploader.component.html',
    inputs:['activeColor','baseColor','overlayColor']
})
export class FileUploaderComponent implements OnInit{
    
    activeColor: string = 'green';
    baseColor: string = '#ccc';
    overlayColor: string = 'rgba(255,255,255,0.5)';
    iconColor:string = '';
    
    dragging: boolean = false;
    loaded: boolean = false;
    imageLoaded: boolean = false;
    imageSrc: string = '';
    borderColor: string = '';

    constructor(private http:HttpService,
                private data:DataService) { }

    ngOnInit(){
        this.imageSrc = this.data.bufAvatar;
    }
    
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

        this.data.bufAvatar = this.imageSrc; 
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