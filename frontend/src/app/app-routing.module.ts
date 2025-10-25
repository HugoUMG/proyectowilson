import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { CuentasComponent } from './cuentas/cuentas.component';

export const routes: Routes = [
  { path: '', component: CuentasComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];
