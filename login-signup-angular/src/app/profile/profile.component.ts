import { Component, OnInit } from '@angular/core';
import { Todo } from '../todo';
import { UserService } from '../user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public todos:Todo[]=[]
  
  constructor(private userService:UserService) { }

  ngOnInit(): void {
    this.userService.getUserTodo().subscribe(data=>{ 
      console.log("user todo data: "+JSON.stringify(data))
      this.todos=data
    }
    )
  }

}
