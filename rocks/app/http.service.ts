import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
 
@Injectable()
export class HttpService{
	
    constructor(private http: Http){ }

    postData(obj:Object){    

 		let headers = new Headers({ 'Content-Type': 'application/json;charset=utf-8' });

		return this.http.post('http://localhost:8080/', JSON.stringify(obj), { headers:headers })
		                .map(res => res.json())
		                .catch((error:string) =>{return Observable.throw(error);});
}