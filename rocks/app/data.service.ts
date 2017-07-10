import { Injectable } from '@angular/core';
import { UserStructure } from './dataStructures';
 
@Injectable()
export class DataService{
	authorizedUser:UserStructure = { email: "", 
                					 password: "",
                					 role: "",
                					 avatar: "" };

	bufAvatar:string="";
}