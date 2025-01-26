import { effect, inject, Injectable, signal } from '@angular/core';
import { Credentials, LoggedInUser } from '../interfaces/credentials';
import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment.development';
import { User } from '../interfaces/user';
import {jwtDecode} from 'jwt-decode';
import { UserRole } from '../enums/user-role';
import { AdoptionRequest, AdoptionRequestDTO } from '../interfaces/adoption-request';
import { catchError, throwError } from 'rxjs';

const API_URL = `${environment.apiURL}`

@Injectable({
  providedIn: 'root'
})
export class UserService {

  

 
  http: HttpClient = inject(HttpClient)
    router = inject(Router);

    user = signal<LoggedInUser | null>(null)

    constructor(){
      const access_token = localStorage.getItem("access_token")
      if (access_token) {
        try {
          const decodedTokenSubject = jwtDecode<any>(access_token);
          console.log('Decoded Token:', decodedTokenSubject);
    
          this.user.set({
            username: decodedTokenSubject.username,
            role: decodedTokenSubject.role,
          });
        } catch (error) {
          console.error('Invalid Token:', error);
          localStorage.removeItem('access_token'); 
        }
      }
      
      effect(() => {
      
        if (this.user()) {
          console.log("User logged in: ", this.user()?.username);
  
          
      
            if (this.user()?.role === 'ADMIN') {
              this.router.navigate(['auth/login/user-profile']);
            } else if (this.user()?.role === 'USER') {
              this.router.navigate(['/auth/login/user-profile']);
            } 
        } else {
          console.log('No user logged in');
          this.router.navigate(['auth/login']);
        }
      })}
  
      isUserLoggedIn(): boolean {
        const access_token = localStorage.getItem("access_token");
        if (!access_token) return false;
    
        try {
          const decodedToken = jwtDecode<any>(access_token);
            return true;
        } catch (error) {
          console.error('Invalid token', error);
          return false;
        }
      }

      submitAdoptionRequest(adoptionRequest: AdoptionRequest) {
        return this.http.post<Map<string, string>>(`${API_URL}/adoption-request`, adoptionRequest)
        .pipe(
          catchError((error: HttpErrorResponse) => {
            if (error.status === 409) {
              if (error.error?.error) {
                alert(error.error.error); 
              } else {
                alert('There is a conflict with your adoption request.');
              }
              this.router.navigate(['/login/user-profile']); 
            } else {
              console.error('Error submitting adoption request:', error);
              alert('Error submitting your request, please try again later');
            }
       
            return throwError(error);
          })
        );
      
      }
      
      getCurrentUser(): LoggedInUser | null {
        const accessToken = localStorage.getItem('access_token');
        
        if (!accessToken) {
          return null;
        }
    
        try {
       
          const decodedToken = jwtDecode<any>(accessToken);  
          console.log('Decoded Token:', decodedToken);

          return {
            username: decodedToken.username,
            role: decodedToken.role,
          };
        } catch (error) {
          console.error('Error decoding token:', error);
          return null;
        }
        }


  
   
  loginUser(credentials: { username: string, password: string }){
    return this.http.post<{access_token: string}>(`${API_URL}/auth/login`,credentials)
}

registerUser(user: User) {
  return this.http.post<{ access_token: string }>(`${API_URL}/auth/signup`, user)
}

logoutUser(){
  this.user.set(null)

    localStorage.removeItem('access_token');
  
 
}

// getUserProfile(username: string) {
//   return this.http.get<User>(`${API_URL}/login/user-details`)
// }

updateUserProfile(user: User){
  return this.http.put<User>(`${API_URL}/login/user-details/${user.username}`, user);
}

getUserByUsername(username: string) {
    return this.http.get<User>(`${API_URL}/login/user-details/${username}`);
  }

 isTokenExpired(token: string): boolean {
    try {
      const decodedToken: any = jwtDecode(token);  
      const expiry = decodedToken.exp * 1000; 
      return Date.now() > expiry;  
    } catch (error) {
      console.error('Invalid token', error);
      return true; 
    }  
 
}  
 }
