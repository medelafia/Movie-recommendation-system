import { Component, inject, OnInit } from '@angular/core';
import { UserServices } from '../../services/user-service/user-services';
import { Router } from '@angular/router';
import { Genre } from '../../models/genre';
import { NgOptimizedImage } from '@angular/common';
import { Series } from '../../models/series';
import { Movie } from '../../models/movie';
import { MovieSkeleton } from '../../components/movie-skeleton/movie-skeleton';

@Component({
  selector: 'app-recommendation',
  imports: [NgOptimizedImage , MovieSkeleton],
  templateUrl: './recommendation.html',
  styleUrl: './recommendation.css',
})
export class Recommendation implements OnInit{
  userServices : UserServices = inject(UserServices) 
  movies? : Movie[] 
  series? :  Series[]
  router : Router = inject(Router)
  loadingMovies : boolean = true
  loadingSeries: boolean = true


  navigate(contentId? : number) { 
    this.router.navigate([`/${contentId}/details`])
  }

  constructGenresNames( arr? : Genre[]) { 
    return arr?.map(element => element.name ).join(', ')
  }
  ngOnInit(): void {
    this.userServices.getRecommendation(this.userServices.getUserId() , "movies").subscribe({
      next : (response) => { 
        this.loadingMovies = false 
        this.movies = response
      } , 
      error : () => {
        this.loadingMovies = false 
        this.movies = []
      }
    }
    )

    this.userServices.getRecommendation(this.userServices.getUserId(), "series").subscribe({
      next : (response) => { 
        this.loadingSeries = false 
        this.series = response
      } , 
      error : () => {
        this.loadingSeries = false 
        this.series = []
      }
    }
    )
  }
}
