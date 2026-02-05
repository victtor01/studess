import { Routes } from '@angular/router';

export const PUBLIC_ROUTES: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'validate',
    loadComponent: () => import('./validate/validate.component').then((m) => m.ValidateComponent),
  },
];
