import { CanActivateFn } from '@angular/router';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { inject } from '@angular/core';


export const authGuard: CanActivateFn = (route, state) => {
  const userService = inject(UserService);
  const router = inject(Router);

  if (userService.user()  ) {
      return true
  }

  return router.navigate(['/login'])
};
