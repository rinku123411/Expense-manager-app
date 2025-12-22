import { Component } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { FirebaseService } from 'src/app/services/firebase.service';
import { FirebaseAuthService } from 'src/app/core/api/firebase/auth.firebase';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
})
export class LoginPage {
  email: string = '';
  password: string = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private firebaseService: FirebaseService,
    private firebaseAuthService: FirebaseAuthService
  ) {}

  login() {
    if (!this.email || !this.password) return;
    this.firebaseAuthService
      .login(this.email, this.password)
      .then(async (res) => {
        console.log(res);
        const user: any = await this.firebaseService.getuserById(res.user.uid);
        localStorage.setItem('user', JSON.stringify(user));
        this.router.navigateByUrl('/homepage');
        console.log('login successful', user);
      })
      .catch((err) => {
        console.error('login failed', err);
      });
  }
}
