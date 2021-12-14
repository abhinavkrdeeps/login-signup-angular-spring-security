import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from './app.constants';
import { Credentials } from './credentials';
import { RegisterModel } from './RegisterModel';

const httpOptions = new HttpHeaders({"content": "application/json"})

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(credentials:Credentials):Observable<any>{
    const url = AppConstants.AUTH_API+"login"
    console.log("Requested URL: "+url)
    console.log("credentials: "+credentials)
    return this.http.post(url,credentials,{headers:httpOptions})
  }

  register(userDetails:RegisterModel):Observable<any>{
    return this.http.post(AppConstants.AUTH_API+"signup",userDetails,{headers:httpOptions})
  }
}
