import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppComponent } from './app.component';
import { AppConstants } from './app.constants';

const httpOptions = new HttpHeaders({'content':'application/json'})

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  getUserPage():Observable<any>{
    return this.http.get(AppConstants.API_URL+"/user",{responseType:'text'})

  }

  getUserTodo():Observable<any>{
    return this.http.get("http://localhost:9999/api/user/todos")
  }
}
