import { Component, EventEmitter, Output } from '@angular/core';

import { ValidationService } from './validation.service';

import { SignInStructure } from './dataStructures';

@Component({
  selector: 'sign-in-form',
  templateUrl: 'sign-in-form.component.html',
  providers: [ ValidationService ]
})
export class SignInFormComponent {

	data:SignInStructure={
		email:"",
		password: ""
	}
	error:SignInStructure={
		email: "",
		password:""
	}

	emailColor:string="#6495ed";
	passwordColor:string="#6495ed";

	@Output('userData') outgoingData = new EventEmitter<SignInStructure>();

	constructor(private validation:ValidationService){ }

	public sendData(event, pointer:string){
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
		}

		if(this.data['email'].length!=0 && this.data['password'].length!=0)
		this.outgoingData.emit(this.data);
	}
}
