import { Component, inject, TemplateRef } from '@angular/core';
import { 
  FormGroup, 
  FormControl, 
  ReactiveFormsModule, 
  Validators,
  AbstractControl, 
} from '@angular/forms';
import { User } from '../../../shared/interfaces/user';
import { UserService } from '../../../shared/services/user.service';
import { CommonModule, NgIfContext } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { UserRole } from '../../../shared/enums/user-role';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule,RouterModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent {
  
  userService = inject(UserService);

  route = inject(ActivatedRoute)
  user!:User

  router = inject(Router)

 

  ngOnInit (){

    const accessToken = localStorage.getItem('access_token');

    if (!accessToken) {
      console.error('No access token found');
      this.router.navigate(['/auth/login']); 
      return;
    }

    const currentUser = this.userService.getCurrentUser();
    if (currentUser && currentUser.username) {
      this.userService.getUserByUsername(currentUser.username).subscribe({
        next: (data) => {
          this.user = data;
          console.log('User data fetched:', this.user);
        },
        error: (err) => {
          console.error('Error fetching user', err);
        }
      });
    } else {
      console.error('No user found in localStorage');
      this.router.navigate(['/auth/login']); 
    }
  }

    }

    
    

  
 
      
     
    


  



