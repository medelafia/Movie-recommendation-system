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




@Component({
  selector: 'app-discover',
  imports: [ NgOptimizedImage ,  RatingModule , FormsModule ,  ButtonModule , DrawerModule , CheckboxModule , NgFor , SliderModule , ProgressSpinnerModule , MovieSkeleton],
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

  incrementSize() {  
    this.page += 1 
    this.contentService.getAllContent(this.page).subscribe(
      response =>{
        console.log(response.content)
        this.contents.push(...response.content)
      }
    )
  }
  OnImageError() { 
    return "public/assets/image.png"
  }

  search() { 
    this.page = 0 
    this.contentService.getAllContent(this.page, this.searchKey).subscribe(
      response =>{
        this.contents = response.content 
      }
    )
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
