import { Component, inject } from '@angular/core';
import { AdoptionRequestDTO } from '../../../../../shared/interfaces/adoption-request';
import { AdminService } from '../../../../../shared/services/admin.service';
import { UserService } from '../../../../../shared/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../../../shared/interfaces/user';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdoptionStatus } from '../../../../../shared/enums/adoption-status';

@Component({
  selector: 'app-change-status',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule],
  templateUrl: './change-status.component.html',
  styleUrl: './change-status.component.css'
})
export class ChangeStatusComponent {
  adminService = inject(AdminService);
  userService = inject(UserService);
  router = inject(Router);
  route = inject(ActivatedRoute); 
  adoptionRequest!: AdoptionRequestDTO;
  newStatus?: AdoptionStatus;
  adoptionRequestId!: number;

  statusOptions = Object.values(AdoptionStatus);
  form!: FormGroup;
  ngOnInit() {

    this.adoptionRequestId = Number(this.route.snapshot.paramMap.get('id'));
   console.log(this.adoptionRequestId)
   console.log('Adoption Request:', this.adoptionRequest);

  
    this.loadAdoptionRequest(this.adoptionRequestId);
  }

  updateStatus() {
    if (!this.form.get('newStatus')?.value) {
      alert('Please select a new status before updating.');
      return;
    }
    this.adoptionRequest.adoptionRequestStatus = this.form.get('newStatus')?.value;
    console.log('AdoptionRequest with updated status:', this.adoptionRequest);
    this.adminService.updateAdoptionRequestStatus(this.adoptionRequest.id, this.adoptionRequest).subscribe({
      next: (response) => {
        this.adoptionRequest = response;
        this.router.navigate(['/success']);
      },
      error: (err) => {
        console.error('Error updating status:', err);
        alert('Failed to update status. Please try again.');
      },
    });
}
loadAdoptionRequest(id: number) {
  this.adminService.getAdoptionRequestById(id).subscribe({
    next: (response) => {
      console.log('Adoption Request fetched successfully:', response)
      this.adoptionRequest = response;
      if (this.adoptionRequest) {
        this.form = new FormGroup({
          username: new FormControl({ value: this.adoptionRequest.username, disabled: true }),
          animalName: new FormControl({ value: this.adoptionRequest.animalName, disabled: true }),
          status: new FormControl({ value: this.adoptionRequest.adoptionRequestStatus, disabled: true }),
          newStatus: new FormControl(this.adoptionRequest.adoptionRequestStatus, Validators.required),
        });

        this.newStatus = this.adoptionRequest.adoptionRequestStatus;
      } else {
        console.error('Adoption Request is undefined');
        alert('Error: Adoption Request not found!');
      }
    },
    error: (err) => {
      console.error('Error fetching adoption request:', err);
      alert('Failed to fetch adoption request.');
      this.router.navigate(['/admin/adoption-requests']);
    },
  });
}
}