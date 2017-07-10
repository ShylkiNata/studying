export class UserStructure{
    email: string;
    password: string;
    role: string;
    avatar: string;
}
export class newUserStructure{
    email: string;
    password: string;
    repassword: string;
    role: string;
    avatar: string;
}
export class MenuStructure{
    name: string;
    ref: string;
}
export class SignInStructure{
    email: string;
    password: string;
}
export class SignUpStructure{
    email: string;
    password: string;
    retype: string;
}
export class ErrorStructure{
    body: string;
    flag: boolean; 
}
export class ConfigTabStructure{
    number: number;
    name: string;
    color: string; 
    visibility: boolean;
}
export class CurrentOption{
    key: string;
    index: number;
}
export class TrailPhotos{
    index: number;
    image: string;
}
export class TrailReference{
    index: number;
    name: string;
    path: string;
}
export class TrailEntity{
    name: string;
    whyGo: string;
    complexity: number;
    trailType: number;
    duration: number;
    kids: boolean;
    dogs: boolean;
    description: string;

    constructor(){
        this.name="";
        this.whyGo="";
        this.complexity=1;
        this.trailType=2;
        this.duration=3;
        this.kids=false;
        this.dogs=false;
        this.description="";
    }
}