import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UserService } from '../services/user.service';

export const adminGuard: CanActivateFn = (route, state) => {
   const userService = inject(UserService);
    const router = inject(Router);
    const currentUser = userService.user();
    if (currentUser && currentUser.role === 'ADMIN') {
      return true;
    }
  
    return router.navigate(['login'])
};
