import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { User } from '../../models/user';
import { UserServices } from '../../services/user-service/user-services';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { AuthResponse } from '../../models/auth-response';

@Component({
  selector: 'app-register',
  imports: [ButtonModule , InputTextModule , FloatLabelModule ,FormsModule, ReactiveFormsModule , MessageModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  readonly router : Router = inject(Router)
  readonly userService : UserServices = inject(UserServices) 

  
  isLoading : boolean = false ;
  informationForm : FormGroup = new FormGroup({ 
    username : new FormControl("" , [Validators.required , Validators.maxLength(15) , Validators.minLength(8)]) , 
    firstName : new FormControl("" , [Validators.required , Validators.maxLength(10) , Validators.minLength(2)]) , 
    lastName : new FormControl("" , [Validators.required , Validators.maxLength(10) , Validators.minLength(2)]) , 
    birthDate : new FormControl("" , [Validators.required]) , 
    email : new FormControl("" , [Validators.required , Validators.email]  ) , 
    password : new FormControl("" , [Validators.required , Validators.minLength(8)]) , 
    confirmPassword : new FormControl("" , [Validators.required , Validators.minLength(8)]) , 
  })
  error : string | null = null 

  getFormControl(name : string ) { 
    return this.informationForm.get(name) ; 
  }



  submitForm() { 
    this.isLoading = true  
    if(!this.getFormControl("password")?.valid || this.getFormControl("password")?.value.trim() !== this.getFormControl("confirmPassword")?.value.trim()  ) {
      this.error = "the passwords should be match"
      this.isLoading = false 
      return 
    } 

    if( !this.getFormControl("firstName")?.valid || 
      !this.getFormControl("lastName")?.valid || 
      !this.getFormControl("email")?.valid ||  
      !this.getFormControl("password")?.valid ||  
      !this.getFormControl("birthDate")?.valid ) {
        this.error = "please check if all fields are valid"
        this.isLoading = false 
        return 
      }

    
    const user : User = {
      firstName :  this.getFormControl("firstName")?.value.trim() , 
      lastName : this.getFormControl("lastName")?.value.trim() , 
      email :  this.getFormControl("email")?.value.trim() ,  
      password : this.getFormControl("password")?.value.trim() , 
      birthDate :  this.getFormControl("birthDate")?.value.trim() , 
      username :  this.getFormControl("username")?.value.trim() , 
      enableRecommendationByEmail : false 
    }

    console.log(user)
    this.userService.registerUser(user).subscribe({
      next : (response : AuthResponse) => { 
        Swal.fire({
          title : "success" , 
          timer : 2000 , 
          text : "Registration success" , 
          icon : "success"
        })

        this.router.navigate(['/'])
      } , 
      error : (err : any ) => { 
        this.isLoading = false
        Swal.fire({
          title : "Error" , 
          timer : 2000 , 
          text : err.error.message , 
          icon : "error"
        })
      }
    }
    )
  }

}
