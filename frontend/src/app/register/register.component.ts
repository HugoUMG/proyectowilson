import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class RegisterComponent {
  model = {
    username: '',
    password: '',
    confirmPassword: ''
  };
  error: string | null = null;

  constructor(private router: Router) {}

  register() {
    if (this.model.password !== this.model.confirmPassword) {
      this.error = 'Las contraseñas no coinciden';
      return;
    }
    console.log('Register:', this.model);
    this.router.navigate(['/login']);
  }
}
