export class RegisterModel{
    constructor(
        public displayName:string,
        public email:string,
        public password:string,
        public matchPassword:string
    ){}
}