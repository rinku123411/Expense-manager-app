import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },

  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login.page').then((m) => m.LoginPage),
  },
  {
    path: 'register-user',
    loadComponent: () =>
      import('./pages/register-user/register-user.component').then(
        (m) => m.RegisterUserComponent
      ),
  },

  {
    path: 'homepage',
    loadComponent: () =>
      import('./pages/homepage/homepage.component').then(
        (m) => m.HomepageComponent
      ),
  },
];
