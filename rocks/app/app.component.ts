import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { Response} from '@angular/http';

import { HttpService } from './http.service';
import { DataService } from './data.service';
import { AuthGuardService } from './auth-guard.service';
import { FileService } from './file.service';

import { SignInFormComponent } from './sign-in-form.component';
import { SignUpFormComponent } from './sign-up-form.component';
import { TrailHomeComponent } from './trail-home.component';
import { GeObjectComponent } from './geobject.component';

import { Routes, RouterModule } from '@angular/router';

import { MenuStructure, UserStructure, SignInStructure, SignUpStructure, ErrorStructure } from './dataStructures';

@Component({
    selector: 'my-app',
	templateUrl: 'main-page.html',
	providers: [ /*HttpService,*/ FileService ]
})

export class AppComponent implements OnInit {

	User:UserStructure = { email: "", 
                		   password: "",
                		   role: "",
                		   avatar: "" };

	defaultAvatar: string = "";

	menuItems: MenuStructure;

	Error:ErrorStructure = {
	    body: "",
	    flag: false 		
	} 

	constructor(private http: HttpService, 
			    private guard: AuthGuardService, 
			    private currentData: DataService,
				private file: FileService){ }

	ngOnInit(){
        this.file.getData("menuItems.json")
                 .subscribe((data: Response) => this.menuItems=data.json());

        this.file.getData("defaultAvatar.json")
                 .subscribe((data: Response) => this.defaultAvatar=(data.json()).toString());  // without toString: Failed to load resource: net::ERR_UNKNOWN_URL_SCHEME

        this.User=this.currentData.authorizedUser;

        if(this.User['email']!=""){
			this.closeSignForm();
			this.authorized = true;
			this.guard.can=true;        	
        }

        this.closeAlert();
    }

	shadowIsHidden = false;

	authorized: boolean = false;
	showMenu: boolean = false;

	colorInbg: string = "";
	colorUpbg: string = "";
	colorIn: string = "";
	colorUp: string = "";

	signButton: string = "";
	signInType: boolean = false;
	signUpType: boolean = false;

	sign: string = "Join Us";

	openSignForm(flag: number): void{
		this.shadowIsHidden = true;
		this.changeState(flag);
	}
	closeSignForm(): void{
		this.shadowIsHidden = false;

		this.signButton = '';
		this.signInType = false;
		this.signUpType = false;
	}

	changeState(flag: number): void{ 
		if(flag==1) {
			this.colorUpbg="#fff";
			this.colorInbg="";

			this.colorUp="#2e2e2e";
			this.colorIn="";

			this.signButton="CREATE ACCOUNT"
			this.signUpType=true;
			this.signInType=false;
		}
		else {
			this.colorUpbg="";
			this.colorInbg="#fff";	

			this.colorUp="";
			this.colorIn="#2e2e2e";	

			this.signButton="sign in";
			this.signInType=true;
			this.signUpType=false;
		}	
	}

	receivedData: SignInStructure;
	receivedRegData: SignUpStructure;

	public handleData(userData:SignInStructure){
		this.receivedData = userData;
	}
	public handleRegData(userRegData:SignUpStructure){
		this.receivedRegData = userRegData;
	}

	checkUserData():void {
		if(this.signButton=="sign in"){
				
				this.User['email']=this.receivedData['email'];
				this.User['password']=this.receivedData['password'];

				this.http.postData( {action: 1, email: this.receivedData['email'], password: this.receivedData['password'] } ) 
						 .subscribe( data => this.getAnswer(data, 1),
	   			 		 			 error => this.Error={
					              				body: error,
					               				flag: true } 
					                 );

				this.sign="Sign Out";
		}
		else {
			if(this.receivedRegData['email']!="" && this.receivedRegData['password']!="" && this.receivedRegData['retype']!="" && this.receivedRegData['password']==this.receivedRegData['retype']) {
				this.User['email']=this.receivedRegData['email'];
				this.User['password']=this.receivedRegData['password'];
				this.User['avatar']=this.defaultAvatar;

				this.http.postData( {action: 2, email: this.receivedRegData['email'], password: this.receivedRegData['password'], role:"user", avatar: this.defaultAvatar} ) 
		                 .subscribe( data => this.getAnswer(data, 2),
	   			 		 			 error => this.Error={
					              				body: error,
					               				flag: true } 
					                 );

		        this.sign="Sign Out";
			}
			else 
				this.Error = {
		    		body: "Please, fill the fields coorectly",
		    		flag: true 		
				} 
	    }
	}

	logOut(): void{
		this.authorized = false;

		this.User = { email: "", 
			 	 	  password: "",
			 	 	  role: "",
			 		  avatar: "" };

		this.receivedData={ email:"",
							password: "" };

		this.receivedRegData={ email:"",
							   password: "",
							   retype: "" };

		this.guard.can=false;

		this.currentData.authorizedUser = { email: "", 
                					 	 	password: "",
                					 	 	role: "",
                					 		avatar: "" };
		this.currentData.bufAvatar="";

		this.Error = {
		    body: "",
		    flag: false 		
		}

		this.sign="Join Us";
	}

	getAnswer(answer:Object, action:number):void {
		if(answer['error'].length===0) {
			        	
			this.closeSignForm();
			this.authorized = true;

			this.guard.can=true;

			this.currentData.authorizedUser['email']=this.User['email'];
			this.currentData.authorizedUser['password']=this.User['password'];

			if(action==1) { // sign in - auth
				this.User['role']=answer['success']['role'];
				this.User['avatar']=answer['success']['avatar'];
			}
			else  // sign up - reg
				this.User['role']=answer['success'];

			this.currentData.authorizedUser['avatar']=this.User['avatar'];
			this.currentData.authorizedUser['role']=this.User['role'];		
			
		 }
		else 
			this.Error = {
		    	body: answer['error'],
		    	flag: true 		
			} 	
	}

	showMenuList(){
		if(this.showMenu)
			this.showMenu=false;
		else this.showMenu=true;
	}

	closeAlert(){
		this.Error = {
		    body: "",
		    flag: false 		
		} 		
	}
}