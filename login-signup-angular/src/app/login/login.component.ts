import { Component, OnInit } from '@angular/core';
import { CurrentUser } from '../currentuser';
import { LoginService } from '../login.service';
import { LoginRequest } from '../LoginRequest';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginRequestModel:any={}
  public currentUser:any={}
  constructor(private loginService:LoginService) { }

  ngOnInit(): void {
  }

  login(){
     console.log(this.loginRequestModel)
     this.loginService.login(this.loginRequestModel).subscribe(data=> {
      console.log(data)
      this.currentUser.name = data.user.displayName
      this.currentUser.accessToken = data.user.token
      localStorage.setItem("current_logged_in_user",this.currentUser.name)
       

     })
  }

}
