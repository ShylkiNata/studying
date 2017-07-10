import { Component, OnInit, EventEmitter } from '@angular/core';
import { Response} from '@angular/http';
import { Router } from '@angular/router';

import { UserStructure, newUserStructure, ErrorStructure } from './dataStructures';

import { HttpService } from './http.service';
import { FileService } from './file.service';
import { DataService } from './data.service';
import { AuthGuardService } from './auth-guard.service';

@Component({
    selector: 'user-list',
    templateUrl: 'user-list.component.html',
    providers: [ FileService ]
})
export class UserListComponent implements OnInit {
	
	userList:UserStructure[]=[];
	newUser:newUserStructure={ email: "",
							   password: "",
							   repassword: "",
							   role: "user",
							   avatar: "" };

	roleStructure:string[]=["user","premium user","admin"];

	User:UserStructure = { email: "", 
                		   password: "",
                		   role: "",
                		   avatar: "" };

	Error:ErrorStructure = {
	    body: "",
	    flag: false 		
	}

	bufferEmail:string="";
	defaultAvatar:string="";

	emailCurrentState:string="";

	formHeader:string="";
	formOption:string="";

	shadowIsHidden:boolean=false;

	constructor( private http: HttpService,
				 private file: FileService,
				 private guard: AuthGuardService,
				 private currentData: DataService,
				 private router: Router ){ }

	ngOnInit() {
		this.http.postData( {action: 3} ) // select all users from database
			     .subscribe( data => this.normalizeStructure(data),
	       			 		 error => this.Error={
				              				body: error,
				               				flag: true } 
				                );

        this.file.getData("defaultAvatar.json")
                 .subscribe((data: Response) => this.defaultAvatar=(data.json()).toString());

        this.newUser["avatar"]=this.defaultAvatar; 	

		this.User=this.currentData.authorizedUser;

		this.closeAlert();
	}

	normalizeStructure(answer:Object):void {
		if(answer['error'].length===0) 
			for(let i=0; i<answer['success'].length; i++) {
                this.userList.push( { email: answer['success'][i]['email'], 
                					  password: answer['success'][i]['password'],
                					  role: answer['success'][i]['role'],
                					  avatar: answer['success'][i]['avatar'] } );
			}
		else 
			this.Error = {
			    body: answer['error'],
			    flag: true 		
			}
	}    


	showAddUserForm(event, keyWord:string){

		if(keyWord==='Edit') { 

			let target = event.target || event.srcElement || event.currentTarget;
			let id:string = target.attributes.id.nodeValue; // id элемента по email
			let position = this.userList.indexOf(this.userList.filter(function (val) {
			    return val.email === id; })[0]);

			if( ( (this.User['role']==="user" || this.User['role']==="premium user") && this.User['email']===id) || (this.User['role']==="admin") ) {

				this.formHeader="Edit user profile";

				this.newUser={ email: this.userList[position]['email'],
							   password: this.userList[position]['password'],
							   repassword: this.userList[position]['password'],
							   role: this.userList[position]['role'],
							   avatar: this.userList[position]['avatar'] };

				this.emailCurrentState=this.userList[position]['email'];

				this.currentData.bufAvatar=this.userList[position]['avatar'];

				this.bufferEmail=this.userList[position]['email'];

				this.shadowIsHidden=true;
				this.formOption=keyWord;
			}
			else 
				this.Error = {
				    body: "Error! Unfortunately, you don't have permissions to delete or edit other user data",
				    flag: true 		
				}
		}
		else 
			if(this.User['role']==="admin") { 
				this.formHeader="Add new user";	

				this.newUser={ email: "",
							   password: "",
							   repassword: "",
							   role: "user",
							   avatar: this.defaultAvatar };

				this.currentData.bufAvatar=this.defaultAvatar;

				this.shadowIsHidden=true; // turn on button to add user
				this.formOption=keyWord;			
			}
	}

	deleteUser(event){ // Don't edit. Working.
		if(this.User['role']!="user" && this.User['role']!="premium user") {

			let target = event.target || event.srcElement || event.currentTarget;
		    let id:string = target.attributes.id.nodeValue; // id элемента по email
		    let position = this.userList.indexOf(this.userList.filter(function (val) {
	      		return val.email === id; })[0]); // element index in the data structure
	    

		    if(this.userList[position]['role']!='admin') 
			    this.http.postData( {action: 4, email: id } ) 
					     .subscribe( data => this.hearResponse(data, position),
					       			 error => this.Error={
					              				body: error,
					               				flag: true } 
					                );
			else this.Error={ body: "You can't remove the administrator.",
					          flag: true }
			}
		else 
			this.Error = {
			    body: "Error! Unfortunately, you don't have permissions to delete or edit user data",
			    flag: true 		
		} 
	}

	hearResponse(answer:Object, index:number){
		if(answer['error'].length===0) {
			this.userList.splice(index, 1); // delete element
		}
		else
			this.Error = {
			    body: answer['error'],
			    flag: true 		
			}
	}

	closeSignForm(){
		if(this.shadowIsHidden)
			this.shadowIsHidden=false;
		else this.shadowIsHidden=true;
	}

	getResult(answer:Object, action:number) {
		if(answer['error'].length===0) {
			if(action==2) 
				this.userList.push( { email: this.newUser['email'], 
	               					  password: this.newUser['password'],
	               					  role: this.newUser['role'],
	               					  avatar: this.newUser['avatar'] } );
			else {
				let id=this.bufferEmail;
			    let index = this.userList.indexOf(this.userList.filter(function (val) {
		      		return val.email === id; })[0]); // позиция элемента в структуре данных
				
				this.userList[index] = { email: this.newUser['email'], 
	               					  	 password: this.newUser['password'],
	               					  	 role: this.newUser['role'],
	               					  	 avatar: this.newUser['avatar'] } ;
	               					  	 
	            if(this.bufferEmail===this.User['email']) {
		            this.currentData.authorizedUser = { email: this.newUser['email'],
		            									password: this.newUser['password'],
														role: this.newUser['role'],
														avatar: this.newUser['avatar'] };

					this.User=this.currentData.authorizedUser;

					this.bufferEmail="";
				}	            			    
			}
			this.closeSignForm();
		}
		else
			this.Error = {
			    body: answer['error'],
			    flag: true 		
			} 					
	}

	addNewUser(){
		if(this.newUser['password']!=this.newUser['repassword'])
			this.Error = {
			    body: "The entered passwords do not match",
			    flag: true 		
			}
		else if(this.newUser['email']=="" || this.newUser['password']=="")
			this.Error = {
			    body: "Please, fill all fields",
			    flag: true 		
			}
		else {
			let seqNumber=2;  	// add
			
			if(this.formHeader!="Add new user")
				seqNumber=5;	// edit

				let id=this.newUser['email'];
			    let index = this.userList.indexOf(this.userList.filter(function (val) {
		      		return val.email === id; })[0]); 

			    if(index==-1 || (this.emailCurrentState==this.newUser['email']) ) { // disable smart premium

					this.newUser['avatar']=this.currentData.bufAvatar;

					this.http.postData( {action: seqNumber, email: this.newUser['email'], password: this.newUser['password'], role: this.newUser['role'], avatar: this.newUser['avatar'], additional: this.bufferEmail } ) 
					     	 .subscribe( data => this.getResult(data, seqNumber),
	       			 		 			 error => this.Error={
						              				body: error,
						               				flag: true } 
						                 );
				}
				else 
					this.Error = {
					    body: "This email is already in use",
					    flag: true 		
					} 
		}
	}

	logOut(){
		this.currentData.authorizedUser = { email: "", 
                					 		password: "",
                					 		role: "",
                					 		avatar: "" };

		this.closeAlert();

		this.guard.can=false;

		this.router.navigate(['']);	
	}

	closeAlert(){
		this.Error = {
		    body: "",
		    flag: false 		
		} 		
	}
}