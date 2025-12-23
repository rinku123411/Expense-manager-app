import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { IonApp, IonRouterOutlet } from '@ionic/angular/standalone';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  imports: [IonApp, IonRouterOutlet],
})
export class AppComponent {
  constructor(private router: Router) {}
  ngOnInit() {
    const token = localStorage.getItem('auth_token');

    if (token) {
      this.router.navigate(['/homepage']);
    } else {
      this.router.navigate(['/login']);
    }
  }
}
