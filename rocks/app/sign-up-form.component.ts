import { Component, EventEmitter, Output } from '@angular/core';

import { ValidationService } from './validation.service';

import { SignUpStructure } from './dataStructures';

@Component({
  selector: 'sign-up-form',
  templateUrl: 'sign-up-form.component.html',
  providers: [ ValidationService ]
})
export class SignUpFormComponent {

	data:SignUpStructure={
		email:"",
		password: "",
		retype: ""
	}
	error:SignUpStructure={
		email: "",
		password:"",
		retype:""
	}

	emailColor:string="#6495ed";
	passwordColor:string="#6495ed";
	retypeColor:string="#6495ed";

	@Output('userRegData') outgoingData = new EventEmitter<SignUpStructure>();

	constructor(private validation:ValidationService){ }

	public sendRegData(event, pointer:string){
		switch(pointer) {
			case 'email':
					this.error['email']=this.validation.checkEmail(this.data['email']);
					if(this.error['email'].length!=0) 
						this.emailColor="#fe2e2e";
					else 
						this.emailColor="#0b610b";					
				break;
			case 'password':
					this.error['password']=this.validation.checkPassword(this.data['password'])
					if(this.error['password'].length!=0) 
						this.passwordColor="#fe2e2e";	
					else 
						this.passwordColor="#0b610b";
				break;
			case 'retype':
					this.error['retype']=this.validation.checkRePassword(this.data['password'],this.data['retype'])
					if(this.error['retype'].length!=0)
						this.retypeColor="#fe2e2e";
					else 
						this.retypeColor="#0b610b";	
				break;
		}

		this.outgoingData.emit(this.data);
	}
}
