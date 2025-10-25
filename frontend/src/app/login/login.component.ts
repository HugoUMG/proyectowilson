import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class LoginComponent {
  model = {
    username: '',
    password: ''
  };
  error: string | null = null;

  constructor(private router: Router) {}

  login() {
    // TODO: Implementar lógica de login
    console.log('Login:', this.model);
    this.router.navigate(['/']);
  }
}
