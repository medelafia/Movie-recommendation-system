import { Component, inject, OnInit } from '@angular/core';
import { TagModule } from 'primeng/tag';
import { UserServices } from '../../services/user-service/user-services';
import { Time } from '@angular/common';
import { SelectModule } from 'primeng/select';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { RouterLink } from '@angular/router';


interface HistoryModel { 
  date : Date 
  time : Time 
  contentTitle : string 
  historyType : Object , 
  contentId : number
}
@Component({
  selector: 'app-history',
  imports: [TagModule , SelectModule , FormsModule , ButtonModule , RouterLink],
  templateUrl: './history.html',
  styleUrl: './history.css',
})
export class History implements OnInit {
  userServices : UserServices = inject(UserServices) 
  histories? : HistoryModel[] 
  filtredHistories ?: HistoryModel[]

  types = [
    {name : "all" , code : "ALL"} , 
    {name : "like" , code : "LIKE"} , 
    {name : "dislike" , code : "DISLIKE"} , 
    {name : "rating" , code : "RATING"} , 
    {name : "review" , code : "REVIEW"} , 

  ] 
  selectedType? : {name : string , code :string } 

  filter(event : any) { 
    if(this.selectedType?.code == "ALL") { 
      this.filtredHistories = this.histories
      return 
    }

    this.filtredHistories = this.histories?.filter(element => element.historyType == this.selectedType?.code)
  }

  ngOnInit(): void {
    this.userServices.getUserHistory(this.userServices.getUserId()).subscribe(
      response => { 
        this.histories = response 
        this.filtredHistories = this.histories
        console.log(this.histories)
      }
    )
  }

}
