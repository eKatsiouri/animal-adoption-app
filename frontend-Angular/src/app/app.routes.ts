import { Routes } from '@angular/router';
import { AboutComponent } from './components/about/about.component';
import { AllAnimalsComponent } from './components/menu/all-animals/all-animals.component';
import { AnimalsForAdoptionComponent } from './components/menu/animals-for-adoption/animals-for-adoption.component';
import { AdoptionRequestComponent } from './components/menu/adoption-requests/adoption-requests.component';
import { MenuComponent } from './components/menu/menu.component';
import { UserLoginComponent } from './components/user-login/user-login.component';
import { UserRegistrationComponent } from './components/user-registration/user-registration.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { AnimalsMoreInfoComponent } from './components/animals-more-info/animals-more-info.component';
import { UserProfileComponent } from './components/login/user-profile/user-profile.component';
import { adminGuard } from './shared/guards/admin-guard.guard';
import { authGuard } from './shared/guards/auth-guard.guard';
import { EditComponent } from './components/login/user-profile/edit/edit.component';
import { SuccessComponent } from './components/success/success.component';
import { AdminAnimalsComponent } from './components/admin/admin-menu/admin-animals/admin-animals.component';
import { AdminAdoptionReqComponent } from './components/admin/admin-menu/admin-adoption-req/admin-adoption-req.component';

import { AdminMenuComponent } from './components/admin/admin-menu/admin-menu.component';
import { EditAnimalComponent } from './components/admin/admin-menu/admin-animals/edit-animals/edit-animals.component';
import { CreateAnimalsComponent } from './components/admin/admin-menu/admin-animals/create-animals/create-animals.component';
import { ChangeStatusComponent } from './components/admin/admin-menu/admin-adoption-req/change-status/change-status.component';

export const routes: Routes = [
    { path: "menu/all-animals", component: AllAnimalsComponent },
    {path: "menu/animals-for-adoption",component:AnimalsForAdoptionComponent},
    {path: "adoption-request/:id", component: AdoptionRequestComponent, canActivate: [authGuard]},
    {path:"menu", component:MenuComponent},
    {path:"",component:HomepageComponent},
    {path:"home",component:HomepageComponent},
    {path:"about",component:AboutComponent},
    {path: "auth/login", component:UserLoginComponent},
    {path:"register",component:UserRegistrationComponent},
    {path: 'all-animals/:id', component: AnimalsMoreInfoComponent},
    {path:"login/user-profile", component: UserProfileComponent},
    { path: 'login/user-profile/:username', component: UserProfileComponent },
   { path: 'success', component: SuccessComponent },
    {path: "adoption-request", component:AdoptionRequestComponent, canActivate: [authGuard]},
    { path: 'login/user-profile/:username/edit', component: EditComponent, canActivate: [authGuard]},
    { path: 'login/admin', component: AdminMenuComponent },
    { path: 'admin/animals', component: AdminAnimalsComponent },
    { path: 'admin/adoptions', component: AdminAdoptionReqComponent },
    { path: 'admin/animals/:id/edit', component:EditAnimalComponent},
    {path: 'admin/animals/create', component: CreateAnimalsComponent },
    { path: 'admin/adoption/update/:id', component:ChangeStatusComponent  },
];
