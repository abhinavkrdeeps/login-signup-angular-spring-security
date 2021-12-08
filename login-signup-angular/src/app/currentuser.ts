export class CurrentUser{
    constructor(
        public accessToken:string,
        public userId: string,
        public displayName:string,
        public roles: string
    ){}
}