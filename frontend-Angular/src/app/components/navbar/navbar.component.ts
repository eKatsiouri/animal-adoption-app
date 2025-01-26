import { Component, effect, inject } from '@angular/core';
import { Router, RouterLink,RouterLinkActive } from '@angular/router';
import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  isLoggedIn: boolean = false;
  userService = inject(UserService);
  user = this.userService.user;
  router = inject(Router);


  constructor() {
  
    effect(() => {
      this.isLoggedIn = !!this.user(); 
      
     
    });
  }


  onLoginClick() {
    if (this.isLoggedIn) {
      const confirmLogout = confirm('You are already logged in. Do you want to logout?');
      if (confirmLogout) {
        this.logout();
      }
    } else {
      this.router.navigate(['/auth/login']);
    }
  }

  logout(){
    this.userService.logoutUser();
    this.router.navigate(['/auth/login']);
}

onProfileClick() {
  const currentUser = this.user();  
  if (this.isLoggedIn ) {
    this.router.navigate([`/login/user-profile/${currentUser?.username}`]);
  } else {
    console.warn('User is not logged in or username is missing.');
  }
}}
