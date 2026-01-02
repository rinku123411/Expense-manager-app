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
    path: 'create-group',
    loadComponent: () =>
      import('./pages/create-group/create-group.page').then(
        (m) => m.CreateGroupPage
      ),
  },

  {
    path: 'homepage',
    loadComponent: () =>
      import('./pages/homepage/homepage.component').then(
        (m) => m.HomepageComponent
      ),
  },
  {
    path: ':groupId/dashboard',
    loadComponent: () =>
      import('./pages/group-dashboard/group-dashboard.component').then(
        (m) => m.GroupDashboardComponent
      ),
  },
];
