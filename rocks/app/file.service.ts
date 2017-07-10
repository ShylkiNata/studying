import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
 
@Injectable()
export class FileService{
 
    constructor(private http: Http){ }
     
    getData(file:string){
        return this.http.get('app/'+file)
    }
}