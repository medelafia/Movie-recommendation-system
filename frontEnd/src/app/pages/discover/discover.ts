import { NgFor, NgOptimizedImage } from '@angular/common';
import { Component, inject , OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { DrawerModule } from 'primeng/drawer';
import { RatingModule } from 'primeng/rating';
import { SliderModule } from 'primeng/slider';
import { Content } from '../../models/content';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { Genre } from '../../models/genre';
import { ContentService } from '../../services/content-service/content-service';
import { MovieSkeleton } from '../../components/movie-skeleton/movie-skeleton';
import { SelectModule } from 'primeng/select';
import { Observable } from 'rxjs';




@Component({
  selector: 'app-discover',
  imports: [ NgOptimizedImage ,  RatingModule , FormsModule ,  ButtonModule , DrawerModule , CheckboxModule , NgFor , SliderModule , ProgressSpinnerModule , MovieSkeleton , SelectModule],
  templateUrl: './discover.html',
  styleUrl: './discover.css' , 
  standalone : true
})
export class Discover implements OnInit {

  protected searchKey : string = ""

  private contentService : ContentService = inject(ContentService)
  private router = inject(Router)

  genres : Genre[] = [ {key : "action" , name : "action" } ]

  selectedGenre : Genre[] = []
  rangeValues : number[] = [1990 ,2020 ]

  contents : Content[] = []
  isLoading : boolean = true 
  page : number = 0 ; 


  visible = false

  types = [
    {name : "all" , code : "ALL"} , 
    {name : "movies" , code : "MOVIES"} , 
    {name : "series" , code : "SERIES"} , 
  ]

  selectedType? : {name : string , code :string } = { name : "all" , code : "ALL"} 


  fetchContent(type : string) { 
    const observable : Observable<any> = 
      this.selectedType?.code == "ALL" ? 
      this.contentService.getAllContent(this.page , this.searchKey)  : 
      (
        this.selectedType?.code == "MOVIES" ? 
          this.contentService.getAllMovies(this.page , this.searchKey) : 
          this.contentService.getAllSeries(this.page , this.searchKey)
      ) ; 

    observable.subscribe({
      next : (response) => { 
        if(type == "UPDATE") 
          this.contents = response.content
        else if(type == "APPEND") 
          this.contents.push(...response.content)
          
      } 
    })
  }

  filter(event : any) { 
    this.fetchContent("UPDATE") 
  }

  incrementSize() {  
    this.page += 1 
    this.fetchContent("APPEND")
  }

  OnImageError() { 
    return "public/assets/image.png"
  }

  search() { 
    this.page = 0 
    this.fetchContent("UPDATE") 
  }

  navigate(contentId? : number) { 
    this.router.navigate([`/${contentId}/details`])
  }

  constructGenresNames( arr? : Genre[]) { 
    return arr?.map(element => element.name ).join(', ')
  }
  ngOnInit(): void {
    this.contentService.getAllContent(this.page ).subscribe(
      response =>{
        this.contents = response.content
        this.isLoading = false
      }
    )
  }

}
