import { Routes } from '@angular/router';
import { PRIVATE_ROUTES } from './features/(private)/private.routes';
import { PUBLIC_ROUTES } from './features/(public)/public.routes';

export const routes: Routes = [...PUBLIC_ROUTES, ...PRIVATE_ROUTES];
