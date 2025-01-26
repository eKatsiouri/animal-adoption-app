import { Component, inject } from '@angular/core';
import { 
  FormGroup, 
  FormControl, 
  ReactiveFormsModule, 
  Validators,
  AbstractControl, 
} from '@angular/forms';
import { User } from '../../shared/interfaces/user';
import { UserRole } from '../../shared/enums/user-role';
import { UserService } from '../../shared/services/user.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './user-registration.component.html',
  styleUrl: './user-registration.component.css'
})
export class UserRegistrationComponent {
  userService = inject(UserService)

  registrationStatus: { success: boolean, message: string } = {
      success:false,
      message: "Not attempted yet"
  }

  form = new FormGroup({
          firstName: new FormControl('', Validators.required),
          lastName: new FormControl('',Validators.required),
          email: new FormControl('', [Validators.required, Validators.email]),
          password: new FormControl('', [Validators.required, Validators.minLength(4)] ),
          confirmPassword: new FormControl('', [Validators.required, Validators.minLength(4)]),
          phoneNumber: new FormControl('',[Validators.required]),
          address: new FormControl(''),
          ssn: new FormControl('',Validators.required),
          username : new FormControl('',Validators.required),
          
         
      },
      this.passwordConfirmPasswordValidator
  )

  passwordConfirmPasswordValidator(control: AbstractControl):{ [key:string] :boolean } | null {
      const form = control as FormGroup;
      const password = form.get('password')?.value;
      const confirmPassword = form.get('confirmPassword')?.value;

      if( password && confirmPassword && password!=confirmPassword) {
          form.get("confirmPassword")?.setErrors({ passwordMismatch: true });
          return { passwordMismatch: true };

      }

      return null
  }

  onSubmit(){
        
      this.checkOnSubmit();
               
  }

  registerAnother(){
      this.form.reset();
      this.registrationStatus = {success:false, message:'Not attempted yet'}
  }

   

  checkOnSubmit(){
     
      
      const user: User = {
        firstname: this.form.get('firstName')?.value || '',
        lastname: this.form.get('lastName')?.value || '',
        email: this.form.get('email')?.value || '',
        password: this.form.get('password')?.value || '',
        phoneNumber: this.form.get('phoneNumber')?.value || '',
        address: this.form.get('address')?.value || '',
        ssn: this.form.get('ssn')?.value || '',
        username: this.form.get('username')?.value || '',
        role: UserRole.USER,
        id: 0
      }
      
    
                  this.userService.registerUser(user).subscribe({
                      next: (response) => {
                          this.registrationStatus = {success: true, message: "Registered"}
                          this.form.reset()
                      },
                      error: (response) =>{
                          console.log("Errors",response)
    
                          this.registrationStatus = {success: false, message: response.error}
                      }
                  })
                  this.form.reset({
                    firstName: '',
                    lastName: '',
                    email: '',
                    password: '',
                    confirmPassword: '',
                    phoneNumber: '',
                    address: '',
                    ssn: '',
                    username: ''
                  });
      }
      
    }
