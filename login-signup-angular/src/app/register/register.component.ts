import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { RegisterModel } from '../RegisterModel';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public newUser: RegisterModel = new RegisterModel("","","","");
  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
  }

  registerUser(){
    console.log(this.newUser)
    this.loginService.registerNewUser(this.newUser).subscribe(data=>this.newUser=data)
  }

}
