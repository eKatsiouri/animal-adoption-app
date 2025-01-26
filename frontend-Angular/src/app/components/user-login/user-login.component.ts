import { Component, inject } from '@angular/core';
import { ReactiveFormsModule,FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../shared/services/user.service';
import { Router } from '@angular/router';
import { Credentials, LoggedInUser } from '../../shared/interfaces/credentials';
import { jwtDecode } from 'jwt-decode';


@Component({
  selector: 'app-user-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent {
  userService = inject(UserService);
  router = inject(Router);

  invalidLogin = false;

  form = new FormGroup({
    password: new FormControl('', Validators.required),
      username: new FormControl('',Validators.required)
  })

  OnSubmit(){
    const credentials = this.form.value as Credentials;
    this.userService.loginUser(credentials).subscribe({
      next: (response) => {
        const access_token = response.access_token;
        console.log('Access Token:', access_token);

  
        if (access_token) {
          const decodedTokenSubject = jwtDecode(access_token) as { sub: LoggedInUser };
          console.log('Decoded Token:', decodedTokenSubject);

          
          this.userService.user.set({
            username: decodedTokenSubject.sub.username,
            role: decodedTokenSubject.sub.role,
          });
          const username = decodedTokenSubject.sub.username

          localStorage.setItem('access_token', access_token);
          this.router.navigate([`/login/user-profile`]);
        }
      },
      error: (error) => {
        console.log('Login Error', error);
        this.invalidLogin = true;
      }
    });
  }
    

  }

