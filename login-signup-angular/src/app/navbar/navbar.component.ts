import { Component, OnInit } from '@angular/core';
import { CurrentUser } from '../currentuser';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  public currentUser: any=""
  constructor() {
    if(localStorage.getItem("current_logged_in_user")!==undefined){
      this.currentUser=localStorage.getItem("current_logged_in_user")
    }else{
      this.currentUser="Please Login First"
    }
   }

  ngOnInit(): void {
    if(localStorage.getItem("current_logged_in_user")!==undefined){
      this.currentUser=localStorage.getItem("current_logged_in_user")
    }else{
      this.currentUser="Please Login First"
    }
  }

}
