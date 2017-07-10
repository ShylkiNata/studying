import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
 
@Injectable()
export class ValidationService{

    checkEmail(email:string): string{    
		let pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    	if(pattern.test(email))
    		return "";
    	else return "Invalid user email";
 	}

 	checkPassword(password:string):string{
 	 	if(password.length==0)
 			return "Enter your password";
 		if(password.length<5)
 			return "Password must be at least 5 characters long";

 		return "";
 	}

  	checkRePassword(password:string, repass:string):string{
 	 	if(repass.length==0)
 			return "Fill the re-entered password field";

 		if(repass!=password || repass.length<5)
 			return "Invalid re-entered password";

 		return "";
 	}
}