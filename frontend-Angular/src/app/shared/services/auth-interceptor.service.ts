import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { UserService } from './user.service';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor{
  userService= inject(UserService)
  router = inject(Router)

  intercept(req: HttpRequest<any>, next: HttpHandler, ) {
    const authToken = localStorage.getItem('access_token')
    if (!authToken) {
        return next.handle(req)
    }
    if (authToken) {
      if (this.userService.isTokenExpired(authToken)) {
        console.log('Token expired');
        localStorage.removeItem('access_token');
        this.router.navigate(['auth/login']);
        return next.handle(req)}
      }
    const authRequest = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + authToken)
    });

    return next.handle(authRequest);
}
}

