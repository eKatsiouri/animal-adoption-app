import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../../../shared/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../../shared/interfaces/user';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.css'
})
export class EditComponent {

  userService = inject(UserService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  user!: User;
  userProfileForm!: FormGroup
  



  ngOnInit(): void {
    const username = this.route.snapshot.paramMap.get('username')!;

    this.userService.getUserByUsername(username).subscribe({
      next: (data) => {
        this.user = data;
        this.initForm();
      },
      error: (err) => {
        console.error('Error fetching user', err);
      }
    });
  }

 
  initForm() {
    this.userProfileForm = new FormGroup({
   
        firstName: new FormControl(this.user.firstname, [Validators.required]),
        lastName: new FormControl(this.user.lastname, [Validators.required]),
        username: new FormControl(this.user.username, [Validators.required]),
        email: new FormControl(this.user.email, [Validators.required, Validators.email]),
        phoneNumber: new FormControl(this.user.phoneNumber, [Validators.required]),
        address: new FormControl(this.user.address),
        ssn: new FormControl(this.user.ssn),
        role: new FormControl(this.user.role)
      })
         
                  
    }
  


  onSubmit() {
    if (this.userProfileForm.valid) {
      const updatedUser = this.userProfileForm.value;
      this.userService.updateUserProfile(updatedUser).subscribe({
        next: (data) => {
          console.log('Profile updated successfully', data);
          this.router.navigate(['/login/user-profile', this.user.username]);
        },
        error: (err) => {
          console.error('Error updating user', err);
        }
      });
    }
  }
}

