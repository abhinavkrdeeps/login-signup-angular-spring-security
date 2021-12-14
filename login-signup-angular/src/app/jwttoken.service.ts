import { Injectable } from '@angular/core';
import { CurrentUser } from './currentuser';

@Injectable({
  providedIn: 'root'
})
export class JwttokenService {

  constructor() { }

  signOut(){
    window.sessionStorage.clear()
  }

  public saveToken(token:string){
    window.sessionStorage.removeItem("auth-token")
    window.sessionStorage.setItem("auth-token",token)
  }

  public saveUserInSession(user:CurrentUser){
    window.sessionStorage.removeItem("auth-user")
    window.sessionStorage.setItem("auth-user",JSON.stringify(user))
  }

  public getTokenFromSession():any{
    return window.sessionStorage.getItem("auth-token")
  }

  public getUserFromSession():any{
    return sessionStorage.getItem("auth-user")
  }
}
