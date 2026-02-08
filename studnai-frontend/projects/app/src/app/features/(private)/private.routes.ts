import { HomeLayoutComponent } from '@/layouts/home-layout/home-layout.component';
import { Routes } from '@angular/router';
import { DashboardComponent } from './home/dashboard.component';

export const PRIVATE_ROUTES: Routes = [
  {
    path: '',
    component: HomeLayoutComponent,
    children: [{ path: 'dashboard', component: DashboardComponent }],
  },
];
