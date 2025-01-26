import { Component, inject } from '@angular/core';
import { AdoptionRequestDTO } from '../../../../shared/interfaces/adoption-request';
import { AdminService } from '../../../../shared/services/admin.service';
import { CommonModule, NgIf } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { UserService } from '../../../../shared/services/user.service';
import { User } from '../../../../shared/interfaces/user';

@Component({
  selector: 'app-admin-adoption-req',
  standalone: true,
  imports: [CommonModule,NgIf,RouterLink,FormsModule],
  templateUrl: './admin-adoption-req.component.html',
  styleUrl: './admin-adoption-req.component.css'
})
export class AdminAdoptionReqComponent {

  adoptionRequests: AdoptionRequestDTO[] = [];
  adminService = inject(AdminService);
  userService = inject(UserService);
  user!: User;
  router = inject(Router);
  route = inject(ActivatedRoute); 

  ngOnInit() {
    this.fetchAdoptionRequests();
  }

  fetchAdoptionRequests(): void {
    this.adminService.getAdoptionRequests().subscribe({
      next: (data) => {
        this.adoptionRequests = data;
      },
      error: (err) => {
        console.error('Error fetching adoption requests:', err);
      },
    });
  }

}

