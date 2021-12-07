import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { LoginRequest } from '../LoginRequest';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginRequestModel= new LoginRequest("default","default")
  constructor(private loginService:LoginService) { }

  ngOnInit(): void {
  }

  login(){
     console.log(this.loginRequestModel)
     this.loginService.login(this.loginRequestModel).subscribe(data=> this.loginRequestModel=data)
  }

}
