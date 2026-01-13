import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../models/user';
import { AuthResponse } from '../../models/auth-response';
import { LoginRequest } from '../../models/login-request';
import { JwtService } from '../jwt-service/jwt-service';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class UserServices {
  baseUrl = "http://localhost:8080/api/user"
  baseRecommendationUrl = "http://localhost:8000"
  httpClient : HttpClient = inject(HttpClient) 
  jwtService : JwtService = inject(JwtService)


  login(loginRequest : LoginRequest ) { 
    const httpOptions = {
      headers: new HttpHeaders({
          'Content-Type': 'application/json'
      })
    }
    return this.httpClient.post<AuthResponse>(this.baseUrl + "/auth/login" , loginRequest ,httpOptions )
  }
  registerUser(user : User) : Observable<AuthResponse> { 
    const httpOptions = {
      headers: new HttpHeaders({
          'Content-Type': 'application/json'
      })
    }
    return this.httpClient.post<AuthResponse>(this.baseUrl + "/auth/register" , user , httpOptions)
  }
  getUserInteraction(userId : number , contentId : number ) : Observable<any> { 
    return this.httpClient.get(this.baseUrl + `/${userId}/interactions?contentId=${contentId}`)
  }
  getUserHistory(userId: number) : Observable<any>  { 
    return this.httpClient.get(this.baseUrl + `/${userId}/history` )
  }
  getRecommendation(userId : number , type : string) : Observable<any> { 
    return this.httpClient.get(this.baseRecommendationUrl + `/${userId}/recommendation/${type}`)
  }
  authenticated() : boolean { 
    return this.jwtService.loadToken() != null && !this.jwtService.is_token_expired()
  }
  logout() { 
    this.jwtService.removeToken() 
  }
  getUserId() { 
    const decoded = jwtDecode(this.jwtService.loadToken()!) as any
  
    return decoded.userId 
  }
}
