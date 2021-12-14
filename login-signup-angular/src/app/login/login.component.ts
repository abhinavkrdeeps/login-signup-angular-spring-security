import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppConstants } from '../app.constants';
import { AuthService } from '../auth.service';
import { Credentials } from '../credentials';
import { CurrentUser } from '../currentuser';
import { JwttokenService } from '../jwttoken.service';
import { LoginService } from '../login.service';
import { LoginRequest } from '../LoginRequest';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public credential: Credentials={
    email: '',
    password: ''
  }
  isLoggedIn:boolean=false;
  isLoggedInFailed:boolean=false;
  errorMessage:any=''
  currentUser:any;
  googleURL = AppConstants.GOOGLE_AUTH_URL;
  facebookURL = AppConstants.FACEBOOK_AUTH_URL;
  githubURL = AppConstants.GITHUB_AUTH_URL;
  linkedinURL = AppConstants.LINKEDIN_AUTH_URL;

  constructor(private authService:AuthService,private token: JwttokenService, private userService:UserService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    console.log("googleURL: "+this.googleURL)
    const token = this.route.snapshot.queryParamMap.get('token');
    const error = this.route.snapshot.queryParamMap.get('error');
    console.log("token: "+token)
    if (this.token.getTokenFromSession()) {
      this.isLoggedIn = true;

      this.currentUser = JSON.parse(this.token.getUserFromSession());
      console.log("currentUser: "+this.currentUser)
    }
    else if(token){
        this.token.saveToken(token);
        this.userService.getUserPage().subscribe(
              data => {
                this.login(data);
              },
              err => {
                this.errorMessage = err.error.message;
                this.isLoggedInFailed = true;
              }
          );
    }
    else if(error){
        this.errorMessage = error;
        this.isLoggedInFailed = true;
    }
  }

  onSubmit(){
    console.log("form: "+this.credential)
    this.authService.login(this.credential).subscribe(
      data => {
        this.token.saveToken(data.accessToken),
        this.login(data.user)

      },
      err => {
        this.isLoggedInFailed=true

      }
    )
  }


  login(data: any) {
    console.log("user: "+data)
    this.token.saveUserInSession(data)
    this.isLoggedIn=true
    this.isLoggedInFailed=false
    window.location.reload()
  }

  
}
