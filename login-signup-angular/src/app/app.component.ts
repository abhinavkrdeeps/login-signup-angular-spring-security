import { Component, OnInit } from '@angular/core';
import { CurrentUser } from './currentuser';
import { JwttokenService } from './jwttoken.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public isLoggedIn: boolean =false
  private roles:string[]=[]
  public currentUser: string =""
  title = 'login-signup-angular';

  constructor(private tokenService: JwttokenService){}

  ngOnInit(){
    this.isLoggedIn = !!this.tokenService.getTokenFromSession()
    if(this.isLoggedIn){
      const user = this.tokenService.getUserFromSession()
      this.roles=user.roles
      this.currentUser = user.displayName
    }
  }

  logout(){
    this.tokenService.signOut()
    window.location.reload()
  }
}
