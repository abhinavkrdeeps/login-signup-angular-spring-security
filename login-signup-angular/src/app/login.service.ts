import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { LoginRequest } from './LoginRequest';
import { catchError, Observable, throwError } from 'rxjs';
import { RegisterModel } from './RegisterModel';

@Injectable({
  providedIn: 'root'
})

export class LoginService {

  constructor(private httpClient: HttpClient) {}

  login(loginRequest:LoginRequest):Observable<any>{
    console.log(this.httpClient.post<LoginRequest>("http://localhost:9999/api/auth/login",loginRequest))
   return this.httpClient.post<LoginRequest>("http://localhost:9999/api/auth/login",loginRequest).pipe(catchError(this.handleError))
  }

  registerNewUser(newUser: RegisterModel){
    return this.httpClient.post<RegisterModel>("http://localhost:9999/api/auth/signup",newUser).pipe(catchError(this.handleError))

  }



  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('An error occurred:', error.error);
    } else {
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }
}
