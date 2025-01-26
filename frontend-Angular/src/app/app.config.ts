import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import {  HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import { AuthInterceptorService } from './shared/services/auth-interceptor.service';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
     provideRouter(routes),
      provideHttpClient(withInterceptorsFromDi()),   {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptorService,
    multi: true
}]
};
